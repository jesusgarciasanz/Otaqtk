package com.example.otaqtk.ui.categories

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
import com.example.otaqtk.databinding.ActivityCategoryBinding
import com.example.otaqtk.kitsu_pojo.Data
import com.example.otaqtk.ui.details.manga_info.MangaInfoActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var category: String
    private lateinit var type: String
    private val kitsuRepository: KitsuRepository = KitsuRepository()


    private var offset: Int = 0
    private var isScrolling: Boolean = false
    private var currentItems: Int = 0
    private var totalItems: Int = 0
    private var scrollOutItems: Int = 0
    private var hasNext: Boolean = false

    private lateinit var categoryAdapter: SearchAdapter
    private lateinit var categoryList: MutableList<Data>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category)

        categoryList = arrayListOf()
        category = intent.getStringExtra("category")
        type = intent.getStringExtra("type")
        binding.categoryTitle.text = category


        checkType()
        categoryRecycler()
        getByCategory(type, category, offset)


        initListeners()
    }

    private fun initListeners() {
        binding.buttonAnimeCategory.setOnClickListener {
            selectedAnimeStyle()
            binding.buttonAnimeCategory.isEnabled = false
            binding.buttonMangaCategory.isEnabled = true
            type = Config.ANIME_TYPE
            categoryList.clear()
            categoryAdapter.notifyDataSetChanged()
            offset = 0
            getByCategory(type, category, offset)
        }

        binding.buttonMangaCategory.setOnClickListener {
            selectedMangaStyle()
            binding.buttonAnimeCategory.isEnabled = true
            binding.buttonMangaCategory.isEnabled = true
            type = Config.MANGA_TYPE
            categoryList.clear()
            categoryAdapter.notifyDataSetChanged()
            offset = 0
            getByCategory(type, category, offset)
        }

        binding.backButtonCategory.setOnClickListener {
            finish()
        }
    }

    private fun getByCategory(type: String, filter: String, off: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val call = kitsuRepository.getByCategory(type, filter, off)
            val cat = call.body()
            if (cat != null && call.code() == 200) {
                categoryList.addAll(cat.data)
                hasNext = cat.links.next != null
                offset += Config.LIMIT
                Log.d("perfil", off.toString())
                categoryAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun categoryRecycler() {
        categoryAdapter = SearchAdapter(categoryList, object : ClickListener {
            override fun onClick(vista: View, index: Int) {
                val intent = Intent(this@CategoryActivity, MangaInfoActivity::class.java)
                intent.putExtra("id", categoryList.get(index).id)
                intent.putExtra("type", type)
                startActivity(intent)
            }
        })
        val layoutManager = GridLayoutManager(this, 3)
        binding.categoryRecycler.setHasFixedSize(true)
        binding.categoryRecycler.layoutManager = layoutManager
        binding.categoryRecycler.adapter = categoryAdapter
        binding.categoryRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
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
                    Log.d("indio", scrollOutItems.toString())
                    if (hasNext){
                        getByCategory(type, category, offset)
                    }
                }
            }

        })
    }

    private fun checkType() {
        if (type.equals(Config.ANIME_TYPE)) {
            selectedAnimeStyle()
            binding.buttonAnimeCategory.isEnabled = false
            binding.buttonMangaCategory.isEnabled = true
            type = Config.ANIME_TYPE
        } else {
            selectedMangaStyle()
            binding.buttonAnimeCategory.isEnabled = true
            binding.buttonMangaCategory.isEnabled = true
            type = Config.MANGA_TYPE
        }
    }

    private fun selectedAnimeStyle() {
        binding.buttonAnimeCategory.setBackgroundResource(R.drawable.search_button_shape)
        binding.buttonMangaCategory.setBackgroundResource(R.drawable.unselected_button_shape)
    }

    private fun selectedMangaStyle() {
        binding.buttonMangaCategory.setBackgroundResource(R.drawable.search_button_shape)
        binding.buttonAnimeCategory.setBackgroundResource(R.drawable.unselected_button_shape)
    }
}