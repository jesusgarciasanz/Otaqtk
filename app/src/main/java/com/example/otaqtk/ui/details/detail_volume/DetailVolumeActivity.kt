package com.example.otaqtk.ui.details.detail_volume

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.otaqtk.R
import com.example.otaqtk.api.Config
import com.example.otaqtk.api.KitsuRepository
import com.example.otaqtk.databinding.ActivityDetailVolumeBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailVolumeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailVolumeBinding
    private lateinit var chapterId: String
    private lateinit var chapterType: String

    private val kitsuRepository: KitsuRepository = KitsuRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_volume)

        chapterId = intent.getStringExtra("chapter_id")
        chapterType = intent.getStringExtra("chapter_type")

        getChaptersData(chapterId)
        initListeners()
    }

    private fun initListeners() {
        binding.backButtonChapterExtrainfo.setOnClickListener {
            finish()
        }
    }

    private fun getChaptersData(id: String){
        CoroutineScope(Dispatchers.Main).launch {
            var include: String
            if (chapterType.equals(Config.CHAPTER_TYPE)){
                include = Config.INCLUDE_MANGA
            }else{
                include = Config.INCLUDE_ANIME
            }
            val call = kitsuRepository.getDataEpisodes(chapterType, id, include)
            val data = call.body()
            if (data != null && call.code() == 200){
                if (data.data.attributes.thumbnail != null){
                    Picasso.get().load(data.data.attributes.thumbnail.original).fit().centerCrop().into(binding.imageDetailVolume)
                }
                if(data.data.attributes.titles?.enUs != null){
                    binding.volumeTopTitle.text = data.data.attributes.titles.enUs
                    binding.volumeTitile.text = data.data.attributes.titles.enUs
                }else{
                    binding.volumeTopTitle.text = data.data.attributes.canonicalTitle
                    binding.volumeTitile.text = data.data.attributes.canonicalTitle
                }
                if (data.data.attributes.synopsis != null){
                    binding.volumeSynopsis.text = data.data.attributes.synopsis
                }
                if (data.data.attributes.airdate != null){
                    binding.publishedDateText.text = data.data.attributes.airdate
                }

                if (data.data.attributes.number != null){
                    binding.volumeCountText.text = data.data.attributes.number.toString()
                }else{
                    binding.volumeCountText.text ="-"
                }

                if (data.data.attributes.length != null){
                    binding.volumeLengthText.text = data.data.attributes.length
                }else{
                    binding.volumeLengthText.text = "-"
                }


            }
        }
    }
}