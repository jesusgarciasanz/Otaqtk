package com.example.otaqtk.ui.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.otaqtk.R
import com.example.otaqtk.adapters.CategoryAdapter
import com.example.otaqtk.adapters.ClickListener
import com.example.otaqtk.adapters.TrendingMangasAdapter
import com.example.otaqtk.api.Config
import com.example.otaqtk.api.KitsuRepository
import com.example.otaqtk.databinding.FragmentHomeFragmentBinding
import com.example.otaqtk.kitsu_pojo.Data
import com.example.otaqtk.ui.categories.CategoryActivity
import com.example.otaqtk.ui.details.manga_info.MangaInfoActivity
import com.example.otaqtk.ui.mos_popular.MostPopularActivity
import com.example.otaqtk.ui.search.SearchActivity
import com.example.otaqtk.ui.trending.TrendingActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private val kitsuRepository: KitsuRepository = KitsuRepository()
    private lateinit var binding: FragmentHomeFragmentBinding
    private lateinit var trendingList: MutableList<Data>
    private lateinit var popularList: MutableList<Data>
    private lateinit var categoriesList: MutableList<Data>
    private lateinit var adapter: TrendingMangasAdapter
    private lateinit var popularAdapter: TrendingMangasAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var type: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_fragment, container, false)
        trendingList = arrayListOf()
        popularList = arrayListOf()
        categoriesList = arrayListOf()
        type = Config.ANIME_TYPE


        getTrendingAnimes()
        getPopularAnime()
        getCategories()


        inflateRecycler()
        popularRecycler()
        categoriesAdapter()
        initListeners()
        return binding.root
    }


    private fun initListeners() {
        binding.buttonAnimeHome.setOnClickListener {
            selectedAnimeStyle()
            trendingList.clear()
            getTrendingAnimes()
            popularList.clear()
            getPopularAnime()
            type = Config.ANIME_TYPE
            binding.buttonAnimeHome.isEnabled = false
            binding.buttonMangasHome.isEnabled = true
            binding.searchText.hint = "Search anime"
        }
        binding.buttonMangasHome.setOnClickListener {
            selectedMangasStyle()
            trendingList.clear()
            getTrendingMangas()
            popularList.clear()
            getPopularManga()
            type = Config.MANGA_TYPE
            binding.buttonMangasHome.isEnabled = false
            binding.buttonAnimeHome.isEnabled = true
            binding.searchText.hint = "Search manga"
        }

        binding.searchBarHome.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            intent.putExtra("type", type)
            startActivity(intent)
        }
        binding.searchText.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            intent.putExtra("type", type)
            startActivity(intent)
        }

        binding.buttonSeeMoreTrending.setOnClickListener {
            val intent = Intent(activity, TrendingActivity::class.java)
            intent.putExtra("type", type)
            startActivity(intent)
        }

        binding.buttonSeeMorePopular.setOnClickListener {
            val intent = Intent(activity, MostPopularActivity::class.java)
            intent.putExtra("type", type)
            startActivity(intent)
        }
    }

    fun getPopularManga() {
        CoroutineScope(Dispatchers.Main).launch {
            val call = kitsuRepository.getPopularData(Config.MANGA_TYPE)
            val popular = call.body()
            if (popular != null) {
                popularList.addAll(popular.data)
                popularAdapter.notifyDataSetChanged()
            }
        }
    }

    fun getPopularAnime() {
        CoroutineScope(Dispatchers.Main).launch {
            val call = kitsuRepository.getPopularData(Config.ANIME_TYPE)
            val popular = call.body()
            if (popular != null ) {
                popularList.addAll(popular.data)
                popularAdapter.notifyDataSetChanged()
            }
        }
    }

    fun getTrendingMangas() {
        CoroutineScope(Dispatchers.Main).launch {
            val call = kitsuRepository.getTrendingData(Config.MANGA_TYPE)
            val trending = call.body()
            if (trending != null) {
                trendingList.addAll(trending.data)
                adapter.notifyDataSetChanged()
            } else {
                Log.d("listerror", "lista vacia")
            }
        }
    }

    fun getTrendingAnimes() {
        CoroutineScope(Dispatchers.Main).launch {
            val call = kitsuRepository.getTrendingData(Config.ANIME_TYPE)
            val trendig = call.body()
            if (trendig != null) {
                trendingList.addAll(trendig.data)
                adapter.notifyDataSetChanged()

            } else {
                Log.d("animeerror", "Lista de animes vacia")
            }
        }
    }

    private fun popularRecycler() {
        popularAdapter = TrendingMangasAdapter(popularList, object : ClickListener {
            override fun onClick(vista: View, index: Int) {
                val intent = Intent(activity, MangaInfoActivity::class.java)
                intent.putExtra("id", popularList.get(index).id)
                intent.putExtra("type", type)
                startActivity(intent)
            }

        })
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.recylerMostPopularHome.setHasFixedSize(true)
        binding.recylerMostPopularHome.layoutManager = layoutManager
        binding.recylerMostPopularHome.adapter = popularAdapter
    }

    private fun inflateRecycler() {
        adapter = TrendingMangasAdapter(trendingList, object : ClickListener {
            override fun onClick(vista: View, index: Int) {
                val intent = Intent(activity, MangaInfoActivity::class.java)
                intent.putExtra("id", trendingList.get(index).id)
                intent.putExtra("type", type)
                startActivity(intent)

                //Toast.makeText(activity, trendingList.get(index).id, Toast.LENGTH_SHORT).show()
            }
        })
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerTrendingHome.setHasFixedSize(true)
        binding.recyclerTrendingHome.layoutManager = layoutManager
        binding.recyclerTrendingHome.adapter = adapter
    }

    private fun getCategories() {
        CoroutineScope(Dispatchers.Main).launch {
            val call = kitsuRepository.getCategories()
            val category = call.body()

            if (category != null) {
                categoriesList.addAll(category.data)
                categoryAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun categoriesAdapter(){
        categoryAdapter = CategoryAdapter(categoriesList, object : ClickListener{
            override fun onClick(vista: View, index: Int) {
                val intent = Intent (activity, CategoryActivity::class.java)
                intent.putExtra("category", categoriesList.get(index).attributes.title)
                intent.putExtra("type", type)
                startActivity(intent)
            }

        })
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerCategories.setHasFixedSize(true)
        binding.recyclerCategories.layoutManager = layoutManager
        binding.recyclerCategories.adapter = categoryAdapter
    }
    //BUTTON STYLES
    private fun selectedAnimeStyle() {
        binding.buttonAnimeHome.setBackgroundResource(R.drawable.buttons_shape)
        binding.buttonAnimeHome.setTextColor(Color.parseColor("#3E92CC"))
        binding.buttonMangasHome.setBackgroundResource(R.drawable.unselected_button_shape)
        binding.buttonMangasHome.setTextColor(Color.parseColor("#FDFDFD"))
    }

    private fun selectedMangasStyle() {
        binding.buttonMangasHome.setBackgroundResource(R.drawable.buttons_shape)
        binding.buttonMangasHome.setTextColor(Color.parseColor("#3E92CC"))
        binding.buttonAnimeHome.setBackgroundResource(R.drawable.unselected_button_shape)
        binding.buttonAnimeHome.setTextColor(Color.parseColor("#FDFDFD"))
    }
}