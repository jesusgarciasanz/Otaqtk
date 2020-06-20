package com.example.otaqtk.ui.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.otaqtk.R
import com.example.otaqtk.adapters.ClickListener
import com.example.otaqtk.adapters.TrendingMangasAdapter
import com.example.otaqtk.api.Repository
import com.example.otaqtk.databinding.FragmentHomeFragmentBinding
import com.example.otaqtk.kitsu_pojo.CategoryData
import com.example.otaqtk.kitsu_pojo.Data
import com.example.otaqtk.ui.details.manga_info.MangaInfoActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private val repository: Repository = Repository()
    private lateinit var binding: FragmentHomeFragmentBinding
    private lateinit var trendingList: MutableList<Data>
    private lateinit var categoriesList: MutableList<CategoryData>
    private lateinit var adapter: TrendingMangasAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home_fragment, container, false)
        trendingList = arrayListOf()
        categoriesList = arrayListOf()

        getTrendingAnimes()
        // getCategories()


        inflateRecycler()
        initListeners()
        return binding.root
    }

    private fun inflateRecycler() {
        adapter = TrendingMangasAdapter(trendingList, object : ClickListener{
            override fun onClick(vista: View, index: Int) {
                val intent= Intent(activity, MangaInfoActivity::class.java)
                intent.putExtra("id",trendingList.get(index).id)
                startActivity(intent)

                //Toast.makeText(activity, trendingList.get(index).id, Toast.LENGTH_SHORT).show()
            }

        })
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerTrendingHome.setHasFixedSize(true)
        binding.recyclerTrendingHome.layoutManager = layoutManager
        binding.recyclerTrendingHome.adapter = adapter
    }

    private fun initListeners() {
        binding.buttonAnimeHome.setOnClickListener {
            selectedAnimeStyle()
            trendingList.clear()
            getTrendingAnimes()
            binding.buttonAnimeHome.isEnabled = false
            binding.buttonMangasHome.isEnabled = true
        }
        binding.buttonMangasHome.setOnClickListener {
            selectedMangasStyle()
            trendingList.clear()
            getTrendingMangas()
            binding.buttonMangasHome.isEnabled = false
            binding.buttonAnimeHome.isEnabled = true
        }
    }


    fun getTrendingMangas() {
        CoroutineScope(Dispatchers.Main).launch {
            val call = repository.getTrendingManga()
            val trending = call.body()
            if (trending != null) {
                trendingList.addAll(trending.data)
                adapter.notifyDataSetChanged()
                Log.d("tren", trendingList[0].attributes.canonicalTitle)
                Log.d("trenimage", trendingList[0].attributes.posterImage.medium)
            } else {
                Log.d("listerror", "lista vacia")
            }
        }
    }

    fun getTrendingAnimes() {
        CoroutineScope(Dispatchers.Main).launch {
            val call = repository.getTrendingAnimes()
            val trendig = call.body()
            if (trendig != null) {
                trendingList.addAll(trendig.data)
                adapter.notifyDataSetChanged()

            } else {
                Log.d("animeerror", "Lista de animes vacia")
            }
        }
    }

    /* fun getCategories(){
         CoroutineScope(Dispatchers.IO).launch {
             val call = repository.getCategories()
             val category = call.body()

             if (category!= null){
                categoriesList.addAll(category.data)
                 Log.d("categoria", category.data[1].attributes.title)
             }
         }
     }*/

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