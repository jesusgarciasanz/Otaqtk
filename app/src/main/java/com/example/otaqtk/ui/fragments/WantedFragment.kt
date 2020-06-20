package com.example.otaqtk.ui.fragments

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.otaqtk.R
import com.example.otaqtk.adapters.RatedMangaAdapter
import com.example.otaqtk.adapters.WantedCardAdapter
import com.example.otaqtk.databinding.ActivityRegisterBinding
import com.example.otaqtk.databinding.FragmentWantedBinding
import com.example.otaqtk.pojo.Manga
import com.example.otaqtk.pojo.RatedMangas

class WantedFragment : Fragment() {

    //TODO CAMBAIR ADAPTER Y FORMA DE INFLAR ADAPTER NO SON CORRECTAS ERA UNA PRUEBA

    private lateinit var binding: FragmentWantedBinding
    private lateinit var layoutManager: RecyclerView.LayoutManager

    @SuppressLint("WrongConstant")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, avedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wanted, container, false)


        //todo quitar desde aqui
        layoutManager = GridLayoutManager(activity, 3)
        binding.recyclerWanted.hasFixedSize()
        binding.recyclerWanted.layoutManager = layoutManager
        binding.recyclerWanted
        val mangas = ArrayList<Manga>()
        val mangasRated = ArrayList<RatedMangas>()
        for (num in 1..40) {
            //mangas.add(Manga(R.drawable.kimetsutest, "One piece", "4.8"))
            mangasRated.add(RatedMangas(R.drawable.kimetsutest, "1"))
        }
        val adapter = WantedCardAdapter(mangas)
        val adapter2 = RatedMangaAdapter(mangasRated)
        binding.recyclerWanted.adapter = adapter2
        //todo aqui

        initListeners()
        return binding.root
    }

    private fun initListeners() {
        binding.buttonwantedMangas.setOnClickListener {
            selectedMangaStyle()
            binding.buttonwantedMangas.isEnabled = false
            binding.buttonWantedAnimes.isEnabled = true
        }
        binding.buttonWantedAnimes.setOnClickListener {
            selectedAnimeStyle()
            binding.buttonWantedAnimes.isEnabled = false
            binding.buttonwantedMangas.isEnabled = true
        }
    }

    //Button Styles
    private fun selectedAnimeStyle() {
        binding.buttonWantedAnimes.setBackgroundResource(R.drawable.buttons_shape)
        binding.buttonWantedAnimes.setTextColor(Color.parseColor("#3E92CC"))
        binding.buttonwantedMangas.setBackgroundResource(R.drawable.unselected_button_shape)
        binding.buttonwantedMangas.setTextColor(Color.parseColor("#FDFDFD"))
    }

    private fun selectedMangaStyle() {
        binding.buttonwantedMangas.setBackgroundResource(R.drawable.buttons_shape)
        binding.buttonwantedMangas.setTextColor(Color.parseColor("#3E92CC"))
        binding.buttonWantedAnimes.setBackgroundResource(R.drawable.unselected_button_shape)
        binding.buttonWantedAnimes.setTextColor(Color.parseColor("#FDFDFD"))
    }

}