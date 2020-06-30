package com.example.otaqtk.ui.details.chapter_info

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.otaqtk.R
import com.example.otaqtk.api.Config
import com.example.otaqtk.api.KitsuRepository
import com.example.otaqtk.api.OtaqtkRepository
import com.example.otaqtk.databinding.ActivityChapterInfoBinding
import com.example.otaqtk.kitsu_pojo.DataClass
import com.example.otaqtk.pojo.ExtraInfo
import com.example.otaqtk.ui.details.detail_volume.DetailVolumeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Float

class ChapterInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChapterInfoBinding
    private lateinit var chapterId: String
    private lateinit var chapterType: String
    private lateinit var token: String
    private lateinit var info : ExtraInfo
    private  var status: String = ""
    private lateinit var data : DataClass

    private val kitsuRepository: KitsuRepository = KitsuRepository()
    private val otaqtkRepository =  OtaqtkRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chapter_info)

        chapterId = intent.getStringExtra("chapter_id")
        chapterType = intent.getStringExtra("chapter_type")

        val value: SharedPreferences? = getSharedPreferences("user_token", Context.MODE_PRIVATE )
        token = value?.getString("token", "").toString()

        getChapterData(chapterId)
        initListeners()

        if (token != ""){
            getExtraInfo()
        }
    }

    private fun initListeners() {
            binding.butonMoreInfoChapter.setOnClickListener {
                val intent = Intent(this, DetailVolumeActivity::class.java )
                intent.putExtra("chapter_id", chapterId)
                intent.putExtra("chapter_type", chapterType)
                startActivity(intent)
            }

        binding.backButtonChapterInfo.setOnClickListener {
            finish()
        }
        binding.buttonHaveitChapter.setOnClickListener {
            if (token != ""){
                status = Config.OWNED_TYPE
                createStatus()
            }else{
                Toast.makeText(this, "You need to be logged", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun getChapterData(id: String){
        CoroutineScope(Dispatchers.Main).launch {
            var include: String
            if (chapterType.equals(Config.CHAPTER_TYPE)){
                include = Config.INCLUDE_MANGA
            }else{
                include = Config.INCLUDE_ANIME
            }
            val call = kitsuRepository.getDataEpisodes(chapterType, id, include)
            val content = call.body()
            if (content!= null){
                data = content
                if (data.data.attributes.thumbnail != null){
                    Picasso.get().load(data.data.attributes.thumbnail?.original).fit().centerCrop().into(binding.backgroundImageChapter)
                }

                if (data.data.attributes.thumbnail != null){
                    Picasso.get().load(data.data.attributes.thumbnail?.original).fit().centerCrop().into(binding.imageChapterInfo)
                }

                if(data.data.attributes.titles?.enUs != null){
                    binding.chapterInfoTitle.text = data.data.attributes.titles?.enUs
                }else{
                    binding.chapterInfoTitle.text = data.data.attributes.canonicalTitle

                }

                if (data.data.attributes.synopsis != null){
                    binding.chapterInfoDescription.text = data.data.attributes.synopsis
                }


                if (data.data.attributes.airdate != null){
                    binding.chapterInfoDate.text = data.data.attributes.airdate
                }

                binding.chapterCount.text = data.data.attributes.number.toString()

                binding.mangasCountInfoChapter.text = data.included?.size.toString()
            }

        }

    }

    private fun getExtraInfo(){
        CoroutineScope(Dispatchers.Main).launch {
            val call = otaqtkRepository.getExtraData(chapterType, FirebaseAuth.getInstance().currentUser!!.uid, chapterId, token)
            info = call.body()!!
            if (info != null && call.code() == 200){
                if(info.had){
                    binding.ownedLayout.visibility = View.VISIBLE
                    binding.buttonHaveitChapter.visibility = View.INVISIBLE
                    binding.wantitChapterInfo.visibility = View.INVISIBLE
                    binding.readChapterLayout.visibility = View.VISIBLE
                }else{
                    binding.ownedLayout.visibility = View.INVISIBLE
                    binding.buttonHaveitChapter.visibility = View.VISIBLE
                    binding.wantitChapterInfo.visibility = View.VISIBLE
                    binding.readChapterLayout.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun createStatus(){
        CoroutineScope(Dispatchers.Main).launch {
            val call = otaqtkRepository.postStatus(chapterType, status, token, data)
            val status = call.body()
            if (status != null && call.code() == 200){
                info.had = true
                binding.ownedLayout.visibility = View.VISIBLE
                binding.buttonHaveitChapter.visibility = View.INVISIBLE
                binding.wantitChapterInfo.visibility = View.INVISIBLE
                binding.readChapterLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun deleteStatus(){
        CoroutineScope(Dispatchers.Main).launch {
            val call = otaqtkRepository.deleteStatus(chapterType, FirebaseAuth.getInstance().currentUser!!.uid, chapterId, token)
            val status = call.body()
            if(status != null && call.code() == 200){

            }
        }
    }

    private fun statusList(){
        CoroutineScope(Dispatchers.Main).launch {
            val call = otaqtkRepository.statusList(chapterType, status, token, data)
            val info = call.body()
            if (info != null && call.code() == 200){

            }
        }
    }
}