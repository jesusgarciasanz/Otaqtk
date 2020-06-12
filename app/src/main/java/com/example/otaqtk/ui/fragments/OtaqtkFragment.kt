package com.example.otaqtk.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.otaqtk.R
import com.example.otaqtk.databinding.FragmentOtaqtkBinding

class OtaqtkFragment : Fragment() {

    private lateinit var binding: FragmentOtaqtkBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_otaqtk, container, false)


        initListeners()
        return binding.root
    }

    private fun initListeners() {
        binding.buttonOtaqtkMangas.setOnClickListener {
            selectedMangaStyle()
        }
        binding.buttonOtaqtkAnimes.setOnClickListener {
            selectedAnimeStyle()
        }
    }

    //Button Styles
    private fun selectedAnimeStyle() {
        binding.buttonOtaqtkAnimes.setBackgroundResource(R.drawable.selected_button_shape)
        binding.buttonOtaqtkMangas.setBackgroundResource(R.drawable.unselected_button_shape)
    }

    private fun selectedMangaStyle() {
        binding.buttonOtaqtkAnimes.setBackgroundResource(R.drawable.unselected_button_shape)
        binding.buttonOtaqtkMangas.setBackgroundResource(R.drawable.selected_button_shape)
    }

}