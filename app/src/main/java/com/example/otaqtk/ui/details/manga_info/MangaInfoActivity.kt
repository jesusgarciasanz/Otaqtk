package com.example.otaqtk.ui.details.manga_info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.otaqtk.R
import com.example.otaqtk.api.Config
import com.example.otaqtk.api.Repository
import com.example.otaqtk.databinding.ActivityMangaInfoBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MangaInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMangaInfoBinding
    private lateinit var id: String
    private val repository: Repository = Repository()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manga_info)

        id = intent.getStringExtra("id")
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show()

        getMangaById(id)
        initListeners()
    }

    private fun initListeners() {
        binding.buttonMoreInfo.setOnClickListener {

        }
    }

    private fun getMangaById(id: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val call = repository.getMangaById(id, Config.MANGA_TYPE)
            val data = call.body()
            if (data != null) {
                Picasso.get().load(data.data.attributes.posterImage.large).fit().centerCrop()
                    .into(binding.imageMangaInfo)
                if (data.data.attributes.coverImage.large != null) {
                    Picasso.get().load(data.data.attributes.coverImage.original).fit().centerCrop()
                        .into(binding.backgroundImage)
                }
                binding.mangaInfoName.text = data.data.attributes.titles.en
                binding.mangaInfoDescription.text = data.data.attributes.synopsis
                binding.mangaInfoRating.text = data.data.attributes.averageRating
                binding.mangaInfoDate.text = data.data.attributes.startDate
            }
        }
    }
}