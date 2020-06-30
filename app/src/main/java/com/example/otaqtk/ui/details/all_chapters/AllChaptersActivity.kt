package com.example.otaqtk.ui.details.all_chapters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.otaqtk.R
import com.example.otaqtk.adapters.ChapterAdapter
import com.example.otaqtk.adapters.ClickListener
import com.example.otaqtk.api.Config
import com.example.otaqtk.api.KitsuRepository
import com.example.otaqtk.databinding.ActivityAllChaptersBinding
import com.example.otaqtk.kitsu_pojo.Data
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllChaptersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllChaptersBinding
    private lateinit var id: String
    private lateinit var type: String

    private val kitsuRepository: KitsuRepository = KitsuRepository()

    private lateinit var adapter : ChapterAdapter
    private lateinit var chapterList : MutableList<Data>
    private  var posterImage:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_all_chapters )

        chapterList = arrayListOf()
        id = intent.getStringExtra("id")
        type = intent.getStringExtra("type")

        getMangasById(id)
        getChapterRecycler()

    initListeners()
    }

    private fun initListeners() {
        binding.backButtonAllChapters.setOnClickListener {
            finish()
        }
    }

    private fun getMangasById(id: String){
        CoroutineScope(Dispatchers.Main).launch {
            val includeType : String

            if(type == Config.MANGA_TYPE){
                includeType = Config.CHAPTER_TYPE
            }else{
                includeType = Config.EPISODE_TYPE
            }
            val call = kitsuRepository.getMangaById(id, type, includeType)
            val data = call.body()
            if (data != null){
                posterImage = data.data.attributes.posterImage?.original.toString()
                chapterList.addAll(data.included!!)
                adapter.notifyDataSetChanged()
            }

        }
    }

    private fun getChapterRecycler(){
        adapter = ChapterAdapter(chapterList, object: ClickListener{
            override fun onClick(vista: View, index: Int) {

            }

        }, posterImage)
        val layoutManager = GridLayoutManager(this, 3)
        binding.recyclerAllChapters.setHasFixedSize(true)
        binding.recyclerAllChapters.layoutManager = layoutManager
        binding.recyclerAllChapters.adapter = adapter
    }
}