package com.example.otaqtk.ui.trending

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.otaqtk.R
import com.example.otaqtk.adapters.ClickListener
import com.example.otaqtk.adapters.TrendingTierAdapter
import com.example.otaqtk.api.Config
import com.example.otaqtk.api.KitsuRepository
import com.example.otaqtk.databinding.ActivityTrendingBinding
import com.example.otaqtk.kitsu_pojo.Data
import com.example.otaqtk.ui.details.manga_info.MangaInfoActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrendingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTrendingBinding
    private lateinit var type: String
    private val kitsuRepository: KitsuRepository = KitsuRepository()
    private lateinit var trendingList: MutableList<Data>
    private lateinit var trendingAdapter: TrendingTierAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_trending)
        type = intent.getStringExtra("type")
        trendingList = arrayListOf()

        checkType()
        getTrendingConten()
        inflateRecycler()
        initListeners()
    }

    private fun initListeners() {
        binding.buttonAnimeTrending.setOnClickListener {
            selectedAnimeStyle()
            binding.buttonAnimeTrending.isEnabled = false
            binding.buttonMangasTrending.isEnabled = true
            trendingList.clear()
            getTrendingConten()
            type = Config.ANIME_TYPE

        }

        binding.buttonMangasTrending.setOnClickListener {
            selectedMangasStyle()
            binding.buttonMangasTrending.isEnabled = false
            binding.buttonAnimeTrending.isEnabled = true
            trendingList.clear()
            getTrendingConten()
            type = Config.MANGA_TYPE

        }
        binding.backButtonTrending.setOnClickListener {
            finish()
        }
    }


    private fun getTrendingConten() {
        CoroutineScope(Dispatchers.Main).launch {
            val call = kitsuRepository.getTrendingData(type)
            val trendring = call.body()
            if (trendring != null) {
                trendingList.addAll(trendring.data)
                trendingAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun inflateRecycler() {
        trendingAdapter = TrendingTierAdapter(trendingList, object : ClickListener {
            override fun onClick(vista: View, index: Int) {
                val intent = Intent(this@TrendingActivity, MangaInfoActivity::class.java)
                intent.putExtra("id", trendingList.get(index).id)
                intent.putExtra("type", type)
                startActivity(intent)
            }
        })
        val layoutManager = GridLayoutManager(this, 1)
        binding.recyclerActivityTrending.setHasFixedSize(true)
        binding.recyclerActivityTrending.layoutManager = layoutManager
        binding.recyclerActivityTrending.adapter = trendingAdapter
    }

    private fun checkType(){
        if(type.equals(Config.ANIME_TYPE)){
            selectedAnimeStyle()
            binding.buttonAnimeTrending.isEnabled = false
            binding.buttonMangasTrending.isEnabled = true
            type = Config.ANIME_TYPE
        }else{
            selectedMangasStyle()
            binding.buttonMangasTrending.isEnabled = false
            binding.buttonAnimeTrending.isEnabled = true
            type = Config.MANGA_TYPE
        }
    }
    private fun selectedAnimeStyle() {
        binding.buttonAnimeTrending.setBackgroundResource(R.drawable.search_button_shape)
        binding.buttonMangasTrending.setBackgroundResource(R.drawable.unselected_button_shape)
    }

    private fun selectedMangasStyle() {
        binding.buttonMangasTrending.setBackgroundResource(R.drawable.search_button_shape)
        binding.buttonAnimeTrending.setBackgroundResource(R.drawable.unselected_button_shape)
    }
}