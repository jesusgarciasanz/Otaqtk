package com.example.otaqtk.ui.details.detail_manga

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.otaqtk.R
import com.example.otaqtk.api.Config
import com.example.otaqtk.api.Repository
import com.example.otaqtk.databinding.ActivityDetailMangaBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailMangaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMangaBinding
    private lateinit var id: String
    private lateinit var type: String
    private val repository: Repository = Repository()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail_manga)

        id = intent.getStringExtra("id")
        type = intent.getStringExtra("type")


        if(type == Config.ANIME_TYPE){
            binding.constraintStudios.visibility = View.INVISIBLE
            binding.constraintStudios.visibility = View.INVISIBLE
            binding.detailMangaStudioText.visibility = View.INVISIBLE
            binding.viewStudios.visibility =  View.INVISIBLE
        }
        getContentById(id)
        initListeners()
    }

    private fun initListeners() {
        binding.buttonGobackManga.setOnClickListener{
            finish()
        }
    }

    private fun getContentById(id: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val call = repository.getMangaById(id, type)
            val data = call.body()
            if(data != null){
                if (data.data.attributes.coverImage?.original != null){
                    Picasso.get().load(data.data.attributes.coverImage?.original).fit().centerCrop()
                        .into(binding.imageDetailManga)
                }else{
                    Picasso.get().load(data.data.attributes.coverImage?.large).fit().centerCrop().into(binding.imageDetailManga)
                }

                if(data.data.attributes.synopsis != null){
                    binding.mangaDescription.text = data.data.attributes.synopsis
                }
                if (data.data.attributes.serialization != null){
                    binding.detailMangaStudioText.text = data.data.attributes.serialization
                }else{
                    binding.detailMangaStudioText.text = "-"
                }
                if(data.data.attributes.chapterCount != null){
                    binding.chaptersCountManga.text = data.data.attributes.chapterCount.toString()
                }else{
                    binding.chaptersCountManga.text = "-"
                }
                if (data.data.attributes.status!= null){
                    binding.mangaDetailStatus.text = data.data.attributes.status
                }else{
                    binding.mangaDetailStatus.text = "-"
                }
                if (data.data.attributes.startDate != null){
                    binding.magaDetailStartDate.text = data.data.attributes.startDate
                }else{
                    binding.magaDetailStartDate.text ="-"
                }
                if (data.data.attributes.ageRating != null){
                    binding.detailMangaAge.text = data.data.attributes.ageRating
                }else{
                    binding.detailMangaAge.text = "-"
                }

                if (data.data.attributes.endDate != null){
                    binding.detailMangaEndDate.text = data.data.attributes.endDate
                }else{
                    binding.detailMangaEndDate.text = "-"
                }
                if (data.data.attributes.titles?.en != null){
                    binding.mangaNameText.text = data.data.attributes.titles.en
                }

            }

        }
    }
}