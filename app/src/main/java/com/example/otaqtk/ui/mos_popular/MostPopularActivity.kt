package com.example.otaqtk.ui.mos_popular

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.otaqtk.R
import com.example.otaqtk.adapters.ClickListener
import com.example.otaqtk.adapters.SearchAdapter
import com.example.otaqtk.api.Config
import com.example.otaqtk.api.KitsuRepository
import com.example.otaqtk.databinding.ActivityMostPopularBinding
import com.example.otaqtk.kitsu_pojo.Data
import com.example.otaqtk.ui.details.manga_info.MangaInfoActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MostPopularActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMostPopularBinding
    private lateinit var type : String
    private val kitsuRepository: KitsuRepository = KitsuRepository()
    private lateinit var popularList: MutableList<Data>
    private lateinit var popularAdapter: SearchAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_most_popular)
        popularList = arrayListOf()
        type = intent.getStringExtra("type")
        checkType()
        getPopularContent()
        popularRecycler()
        initListeners()
    }

    private fun initListeners() {
        binding.buttonAnimePopular.setOnClickListener {
            selectedAnimeStyle()
            binding.buttonAnimePopular.isEnabled = false
            binding.buttonMangasPopular.isEnabled = true
            popularList.clear()
            getPopularContent()
            type = Config.ANIME_TYPE
        }
        binding.buttonMangasPopular.setOnClickListener {
            selectedMangasStyle()
            binding.buttonMangasPopular.isEnabled = false
            binding.buttonAnimePopular.isEnabled = true
            popularList.clear()
            getPopularContent()
            type = Config.MANGA_TYPE
        }
        binding.backButtonPopular.setOnClickListener {
            finish()
        }
    }

    private fun getPopularContent(){
        CoroutineScope(Dispatchers.Main).launch {
            val call = kitsuRepository.getPopularData(type)
            val popular = call.body()
            if (popular!= null){
                popularList.addAll(popular.data)
                popularAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun popularRecycler(){
        popularAdapter = SearchAdapter(popularList, object :ClickListener{
            override fun onClick(vista: View, index: Int) {
                val intent = Intent(this@MostPopularActivity, MangaInfoActivity::class.java)
                intent.putExtra("id", popularList.get(index).id)
                intent.putExtra("type", type)
                startActivity(intent)
            }
        })
        val layoutManager = GridLayoutManager(this, 3)
        binding.recyclerPopularActivity.setHasFixedSize(true)
        binding.recyclerPopularActivity.layoutManager = layoutManager
        binding.recyclerPopularActivity.adapter = popularAdapter

    }
    private fun checkType(){
        if (type.equals(Config.ANIME_TYPE)){
            selectedAnimeStyle()
            binding.buttonAnimePopular.isEnabled = false
            binding.buttonMangasPopular.isEnabled = true
            type = Config.ANIME_TYPE
        }else{
            selectedMangasStyle()
            binding.buttonMangasPopular.isEnabled = false
            binding.buttonAnimePopular.isEnabled = true
            type = Config.MANGA_TYPE
        }
    }

    private fun selectedAnimeStyle() {
        binding.buttonAnimePopular.setBackgroundResource(R.drawable.search_button_shape)
        binding.buttonMangasPopular.setBackgroundResource(R.drawable.unselected_button_shape)
    }

    private fun selectedMangasStyle() {
        binding.buttonMangasPopular.setBackgroundResource(R.drawable.search_button_shape)
        binding.buttonAnimePopular.setBackgroundResource(R.drawable.unselected_button_shape)
    }
}