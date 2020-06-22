package com.example.otaqtk.ui.details.manga_info

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.otaqtk.R
import com.example.otaqtk.api.Config
import com.example.otaqtk.api.Repository
import com.example.otaqtk.databinding.ActivityMangaInfoBinding
import com.example.otaqtk.ui.details.detail_manga.DetailMangaActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Float.parseFloat

class MangaInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMangaInfoBinding
    private lateinit var id: String
    private lateinit var type: String
    private val repository: Repository = Repository()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manga_info)

        id = intent.getStringExtra("id")
        type = intent.getStringExtra("type")


        getMangaById(id)
        initListeners()
    }

    private fun initListeners() {
        binding.buttonMoreInfo.setOnClickListener {
            val intent = Intent(this, DetailMangaActivity::class.java)
            intent.putExtra("id",id)
            intent.putExtra("type", type)
            startActivity(intent)
        }
    }

    private fun getMangaById(id: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val call = repository.getMangaById(id, type)
            val data = call.body()
            if (data != null) {
                Picasso.get().load(data.data.attributes.posterImage?.large).fit().centerCrop()
                    .into(binding.imageMangaInfo)
                if (data.data.attributes.coverImage?.large != null) {
                    Picasso.get().load(data.data.attributes.coverImage.original).fit().centerCrop()
                        .into(binding.backgroundImage)
                }
                if (data.data.attributes.titles?.en != null){
                    binding.mangaInfoName.text = data.data.attributes.titles.en
                }else{
                    binding.mangaInfoName.text = data.data.attributes.titles?.enJp
                }
                if (data.data.attributes.synopsis != null){
                    binding.mangaInfoDescription.text = data.data.attributes.synopsis
                }
                if (data.data.attributes.averageRating != null){
                    binding.mangaInfoRating.text = (parseFloat(data.data.attributes.averageRating)/20).toString()
                }
                if (data.data.attributes.startDate != null){
                    binding.mangaInfoDate.text = data.data.attributes.startDate
                }
            }
        }
    }
}