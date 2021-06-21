package com.example.otaqtk.ui.mos_popular

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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


    private var offset: Int = 0
    private var isScrolling: Boolean = false
    private var currentItems: Int = 0
    private var totalItems: Int = 0
    private var scrollOutItems: Int = 0
    private var hasNext: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_most_popular)
        popularList = arrayListOf()
        type = intent.getStringExtra("type")
        checkType()
        getPopularExtra(type, offset)
        popularRecycler()
        initListeners()
    }

    private fun initListeners() {
        binding.buttonAnimePopular.setOnClickListener {
            selectedAnimeStyle()
            binding.buttonAnimePopular.isEnabled = false
            binding.buttonMangasPopular.isEnabled = true
            type = Config.ANIME_TYPE
            popularList.clear()
            offset = 0
            getPopularExtra(type, offset)
            popularAdapter.notifyDataSetChanged()

        }
        binding.buttonMangasPopular.setOnClickListener {
            selectedMangasStyle()
            binding.buttonMangasPopular.isEnabled = false
            binding.buttonAnimePopular.isEnabled = true
            type = Config.MANGA_TYPE
            popularList.clear()
            offset = 0
            getPopularExtra(type, offset)
            popularAdapter.notifyDataSetChanged()

        }
        binding.backButtonPopular.setOnClickListener {
            finish()
        }
    }
    

    private fun getPopularExtra(type: String, off: Int){
        CoroutineScope(Dispatchers.Main).launch {
            val call = kitsuRepository.getPopularExtra(type, off)
            val pop = call.body()
            if (pop != null && call.code() == 200){
                popularList.addAll(pop.data)
                hasNext = pop.links.next != null
                offset += Config.LIMIT
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

        binding.recyclerPopularActivity.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true
                }

            }
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItems = layoutManager.childCount
                totalItems = layoutManager.itemCount
                scrollOutItems = layoutManager.findFirstVisibleItemPosition()

                if (isScrolling && currentItems + scrollOutItems == totalItems) {
                    isScrolling = false
                    if (hasNext){
                        getPopularExtra(type, offset)
                    }
                }
            }
        })

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