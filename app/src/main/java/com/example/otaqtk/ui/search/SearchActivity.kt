package com.example.otaqtk.ui.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.otaqtk.R
import com.example.otaqtk.adapters.ClickListener
import com.example.otaqtk.adapters.SearchAdapter
import com.example.otaqtk.api.Config
import com.example.otaqtk.api.Repository
import com.example.otaqtk.databinding.ActivitySearchBinding
import com.example.otaqtk.kitsu_pojo.Data
import com.example.otaqtk.ui.details.manga_info.MangaInfoActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {
    private val repository: Repository = Repository()
    private lateinit var binding: ActivitySearchBinding
    private lateinit var resultsList: MutableList<Data>
    private lateinit var search: String
    private lateinit var searchAdapter: SearchAdapter
    private var type: String = Config.ANIME_TYPE


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        resultsList = arrayListOf()

        searchRecycler()


        binding.searchActivityText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                search = s.toString()
                searchContent(type, search)
            }

        })

        initListeners()
    }

    private fun initListeners() {
        binding.buttonAnimeSearch.setOnClickListener {
            selectedAnimeStyle()
            binding.buttonAnimeSearch.isEnabled = false
            binding.buttonMangasSearch.isEnabled = true
            type = Config.ANIME_TYPE
            resultsList.clear()
            searchAdapter.notifyDataSetChanged()
            searchContent(type, search)
        }

        binding.buttonMangasSearch.setOnClickListener{
            selectedMangasStyle()
            binding.buttonMangasSearch.isEnabled = false
            binding.buttonAnimeSearch.isEnabled = true
            type = Config.MANGA_TYPE
            resultsList.clear()
            searchAdapter.notifyDataSetChanged()
            searchContent(type, search)
        }
    }

    private fun searchContent(type: String, search: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val call = repository.searchContent(type, search)
            val text = call.body()
            if (text != null) {
                resultsList.clear()
                resultsList.addAll(text.data)
                searchAdapter.notifyDataSetChanged()
            } else {
                Log.d("listerror", "lista vacia")
            }
        }


    }

    private fun searchRecycler() {
        searchAdapter = SearchAdapter(resultsList, object : ClickListener {
            override fun onClick(vista: View, index: Int) {
                val intent = Intent(this@SearchActivity, MangaInfoActivity::class.java)
                intent.putExtra("id", resultsList.get(index).id)
                intent.putExtra("type", type)
                startActivity(intent)

            }
        })
        val layoutManager = GridLayoutManager(this, 3)
        binding.searchRecycler.setHasFixedSize(true)
        binding.searchRecycler.layoutManager = layoutManager
        binding.searchRecycler.adapter = searchAdapter
    }

    private fun selectedAnimeStyle(){
        binding.buttonAnimeSearch.setBackgroundResource(R.drawable.search_button_shape)
        binding.buttonMangasSearch.setBackgroundResource(R.drawable.unselected_button_shape)
    }

    private fun selectedMangasStyle(){
        binding.buttonMangasSearch.setBackgroundResource(R.drawable.search_button_shape)
        binding.buttonAnimeSearch.setBackgroundResource(R.drawable.unselected_button_shape)
    }
}