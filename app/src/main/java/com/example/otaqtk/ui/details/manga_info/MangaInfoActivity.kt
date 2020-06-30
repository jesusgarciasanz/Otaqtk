package com.example.otaqtk.ui.details.manga_info

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.otaqtk.R
import com.example.otaqtk.adapters.ChapterAdapter
import com.example.otaqtk.adapters.ClickListener
import com.example.otaqtk.api.Config
import com.example.otaqtk.api.KitsuRepository
import com.example.otaqtk.api.OtaqtkRepository
import com.example.otaqtk.databinding.ActivityMangaInfoBinding
import com.example.otaqtk.kitsu_pojo.Data
import com.example.otaqtk.kitsu_pojo.DataClass
import com.example.otaqtk.pojo.ExtraInfo
import com.example.otaqtk.ui.details.all_chapters.AllChaptersActivity
import com.example.otaqtk.ui.details.chapter_info.ChapterInfoActivity
import com.example.otaqtk.ui.details.detail_manga.DetailMangaActivity
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Float.parseFloat

class MangaInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMangaInfoBinding
    private lateinit var id: String
    private lateinit var type: String
    private var posterImage: String = ""
    private lateinit var token: String
    private  var status: String = ""
    private lateinit var info : ExtraInfo


    private val kitsuRepository: KitsuRepository = KitsuRepository()
    private val otaqtkRepository =  OtaqtkRepository()
    private lateinit var data : DataClass

    private lateinit var adapter: ChapterAdapter
    private lateinit var chapterList: MutableList<Data>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manga_info)
        chapterList = arrayListOf()
        id = intent.getStringExtra("id")
        type = intent.getStringExtra("type")


        val value: SharedPreferences? = getSharedPreferences("user_token", Context.MODE_PRIVATE )
        token = value?.getString("token", "").toString()


            getMangaById(id)
            getChapterRecycler()
            initListeners()

        if (token != ""){
            getExtraInfo()
        }
    }

    private fun initListeners() {
        binding.buttonMoreInfo.setOnClickListener {
            val intent = Intent(this, DetailMangaActivity::class.java)
            intent.putExtra("id",id)
            intent.putExtra("type", type)
            startActivity(intent)
        }
        binding.backButtonMangaInfo.setOnClickListener {
            finish()
        }
        binding.buttonSeeAllMangainfo.setOnClickListener {
            val intent = Intent(this, AllChaptersActivity::class.java)
            intent.putExtra("id",id)
            intent.putExtra("type", type)
            startActivity(intent)
        }

        binding.mangaInfoLike.setOnClickListener {
            if (token != ""){
                if (info.is_favourite){
                    deleteStatus()
                }else{
                    status = Config.STATUS_FAV
                    createStatus()
                }
            }else{
                Toast.makeText(this, "You need to be logged", Toast.LENGTH_LONG).show()
            }

        }

        binding.buttinHaveitInfo.setOnClickListener{
            if (token != ""){
                status = Config.OWNED_TYPE
                statusList()
            }else{
                Toast.makeText(this, "You need to be logged", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun getMangaById(id: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val includeType : String

            if(type == Config.MANGA_TYPE){
                includeType = Config.CHAPTER_TYPE
            }else{
                includeType = Config.EPISODE_TYPE
            }

            val call = kitsuRepository.getMangaById(id, type, includeType)
            val content = call.body()
            if (content != null){
                data = content
                if (data.data.attributes.posterImage != null){
                    Picasso.get().load(data.data.attributes.posterImage?.large).fit().centerCrop().into(binding.imageMangaInfo)
                    posterImage = data.data.attributes.posterImage?.original.toString()
                }else{
                    Picasso.get().load(data.data.attributes.coverImage?.original).fit().centerCrop().into(binding.imageMangaInfo)
                    posterImage = data.data.attributes.coverImage?.original.toString()
                }

                if (data.data.attributes.coverImage?.original != null) {
                    Picasso.get().load(data.data.attributes.coverImage?.original).fit().centerCrop()
                        .into(binding.backgroundImage)
                }

                if (data.data.attributes.titles?.enUs != null){
                    binding.mangaInfoName.text = data.data.attributes.titles?.enUs
                }else{
                    binding.mangaInfoName.text = data.data.attributes.canonicalTitle
                }

                if (data.data.attributes.synopsis != null){
                    binding.mangaInfoDescription.text = data.data.attributes.synopsis
                }
                if (data.data.attributes.averageRating != null){
                    binding.mangaInfoRating.text =
                        (parseFloat(data.data.attributes.averageRating!!)/20).toString()
                }
                if (data.data.attributes.startDate != null){
                    binding.mangaInfoDate.text = data.data.attributes.startDate
                }
                if (data.data.attributes.chapterCount != null){
                    binding.textMangasCountInfo.text = data.data.attributes.chapterCount.toString()
                }else{
                    binding.textMangasCountInfo.text = data.included?.size.toString()
                }

                if (data.data.attributes.status != "finished"){
                    binding.infoCurrentShape.visibility = View.VISIBLE
                    binding.infoFinishedShape.visibility = View.INVISIBLE
                }else{
                    binding.infoCurrentShape.visibility = View.INVISIBLE
                    binding.infoFinishedShape.visibility = View.VISIBLE
                }

                chapterList.addAll(data.included!!)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun getChapterRecycler(){
        adapter = ChapterAdapter(chapterList, object :ClickListener{
            override fun onClick(vista: View, index: Int) {
                val intent = Intent(this@MangaInfoActivity, ChapterInfoActivity::class.java)
                intent.putExtra("chapter_id", chapterList.get(index).id)
                intent.putExtra("chapter_type", chapterList.get(index).type)
                startActivity(intent)
            }
        }, posterImage)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerMangaInfo.setHasFixedSize(true)
        binding.recyclerMangaInfo.layoutManager = layoutManager
        binding.recyclerMangaInfo.adapter = adapter
    }

    private fun getExtraInfo(){
        CoroutineScope(Dispatchers.Main).launch {
            val call = otaqtkRepository.getExtraData(type, FirebaseAuth.getInstance().currentUser!!.uid, id, token)
            info = call.body()!!
            if (info != null && call.code() == 200){

                if (info.is_collecting){
                    binding.collenctingLayout.visibility = View.VISIBLE
                    binding.mangaInfoLike.visibility= View.VISIBLE
                }else{
                    binding.collenctingLayout.visibility = View.INVISIBLE
                    binding.mangaInfoLike.visibility= View.INVISIBLE
                }
                if (info.is_favourite){
                    binding.mangaInfoLike.setImageResource(R.drawable.ic_like)
                }else{
                    binding.mangaInfoLike.setImageResource(R.drawable.ic_not_followed)
                }

            }
        }
    }

    private fun createStatus(){
        CoroutineScope(Dispatchers.Main).launch {
            val call = otaqtkRepository.postStatus(type, status, token, data)
            val status = call.body()
            if (status!= null && call.code() == 200){
                binding.mangaInfoLike.setImageResource(R.drawable.ic_like)
                info.is_favourite = true
            }
        }
    }

    private fun deleteStatus(){
        CoroutineScope(Dispatchers.Main).launch {
            val call = otaqtkRepository.deleteStatus(type, FirebaseAuth.getInstance().currentUser!!.uid, id, token)
            val status = call.body()
            if (status != null && call.code() == 200){
                binding.mangaInfoLike.setImageResource(R.drawable.ic_not_followed)
            }
        }
    }

    private fun statusList(){
        CoroutineScope(Dispatchers.Main).launch {
            val call = otaqtkRepository.statusList(type, status, token, data)
            val info = call.body()
            if(info!= null && call.code() == 200){
                binding.collenctingLayout.visibility = View.VISIBLE
            }
        }
    }

}