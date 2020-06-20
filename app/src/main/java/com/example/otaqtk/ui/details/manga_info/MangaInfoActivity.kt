package com.example.otaqtk.ui.details.manga_info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.otaqtk.R
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
        initListrsners()
    }

    private fun initListrsners() {
        binding.buttonMoreInfo.setOnClickListener {

        }
    }

    private fun getMangaById(id: String) {
        CoroutineScope(Dispatchers.Main).launch {
            Log.d("identificador", id)
            val call = repository.getMangaById("26004")
            val data = call.body()
            if (data != null) {

                    //Picasso.get().load(data.attributes.posterImage.large).fit().centerCrop().into(binding.backgroundImage)
                   /* binding.mangaInfoName.text = data.attributes.titles.enJp
                    binding.mangaInfoDescription.text = data.attributes.synopsis
                    binding.mangaInfoRating.text = data.attributes.averageRating
                    binding.mangaInfoDate.text = data.attributes.startDate
                    Log.d("titulillo", data.attributes.title + "")*/

            }else{
                Log.d("dataerror", "void data")
        }
    }
}
}