package com.example.otaqtk.ui.details.detail_manga

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.otaqtk.R
import com.example.otaqtk.api.Config
import com.example.otaqtk.api.KitsuRepository
import com.example.otaqtk.api.OtaqtkRepository
import com.example.otaqtk.databinding.ActivityDetailMangaBinding
import com.example.otaqtk.pojo.ExtraInfo
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailMangaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMangaBinding
    private lateinit var id: String
    private lateinit var type: String

    private val kitsuRepository: KitsuRepository = KitsuRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_manga)

        id = intent.getStringExtra("id")
        type = intent.getStringExtra("type")


        if (type == Config.ANIME_TYPE) {
            binding.constraintStudios.visibility = View.INVISIBLE
            binding.constraintStudios.visibility = View.INVISIBLE
            binding.detailMangaStudioText.visibility = View.INVISIBLE
            binding.viewStudios.visibility = View.INVISIBLE
        }


        getContentById(id)
        initListeners()
    }

    private fun initListeners() {
        binding.backButtonDetailManga.setOnClickListener {
            finish()
        }
    }

    private fun getContentById(id: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val includeType: String

            if (type == Config.MANGA_TYPE) {
                includeType = Config.CHAPTER_TYPE
            } else {
                includeType = Config.EPISODE_TYPE
            }

            val call = kitsuRepository.getMangaById(id, type, includeType)
            val data = call.body()

            if (data != null && call.isSuccessful && call.code() == 200) {
                if (data.data.attributes.coverImage?.original != null) {
                    Picasso.get().load(data.data.attributes.coverImage.original).fit().centerCrop()
                        .into(binding.imageDetailManga)
                } else {
                    Picasso.get().load(data.data.attributes.posterImage?.small).fit().centerCrop()
                        .into(binding.imageDetailManga)
                }


                if (data.data.attributes.titles?.enUs != null) {
                    binding.mangaNameText.text = data.data.attributes.titles.enUs
                } else {
                    binding.mangaNameText.text = data.data.attributes.canonicalTitle
                }


                if (data.data.attributes.synopsis != null) {
                    binding.mangaDescription.text = data.data.attributes.synopsis
                }


                if (data.data.attributes.serialization != null) {
                    binding.detailMangaStudioText.text = data.data.attributes.serialization
                } else {
                    binding.detailMangaStudioText.text = "-"
                }


                if (data.data.attributes.chapterCount != null) {
                    binding.chaptersCountManga.text = data.data.attributes.chapterCount.toString()
                } else {
                    binding.chaptersCountManga.text = data.included?.size.toString()
                }


                if (data.data.attributes.status != null) {
                    binding.mangaDetailStatus.text = data.data.attributes.status
                } else {
                    binding.mangaDetailStatus.text = "-"
                }


                if (data.data.attributes.startDate != null) {
                    binding.magaDetailStartDate.text = data.data.attributes.startDate
                } else {
                    binding.magaDetailStartDate.text = "-"
                }


                if (data.data.attributes.ageRating != null) {
                    binding.detailMangaAge.text = data.data.attributes.ageRating
                } else {
                    binding.detailMangaAge.text = "-"
                }



                if (data.data.attributes.endDate != null) {
                    binding.detailMangaEndDate.text = data.data.attributes.endDate
                } else {
                    binding.detailMangaEndDate.text = "-"
                }

            }

        }
    }

}