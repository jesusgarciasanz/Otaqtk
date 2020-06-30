package com.example.otaqtk.ui.fragments

import android.graphics.Color
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
        binding.buttonAnimeOtaqtk.setOnClickListener {
            selectedAnimeStyle()
            binding.buttonAnimeOtaqtk.isEnabled = false
            binding.buttonMangasOtaqtk.isEnabled = true
        }
        binding.buttonMangasOtaqtk.setOnClickListener {
            selectedMangaStyle()
            binding.buttonMangasOtaqtk.isEnabled = false
            binding.buttonAnimeOtaqtk.isEnabled = true
        }
    }



    //Button Styles
    private fun selectedAnimeStyle() {
        binding.buttonAnimeOtaqtk.setBackgroundResource(R.drawable.buttons_shape)
        binding.buttonAnimeOtaqtk.setTextColor(Color.parseColor("#3E92CC"))
        binding.buttonMangasOtaqtk.setBackgroundResource(R.drawable.unselected_button_shape)
        binding.buttonMangasOtaqtk.setTextColor(Color.parseColor("#FDFDFD"))
    }

    private fun selectedMangaStyle() {
        binding.buttonMangasOtaqtk.setBackgroundResource(R.drawable.buttons_shape)
        binding.buttonMangasOtaqtk.setTextColor(Color.parseColor("#3E92CC"))
        binding.buttonAnimeOtaqtk.setBackgroundResource(R.drawable.unselected_button_shape)
        binding.buttonAnimeOtaqtk.setTextColor(Color.parseColor("#FDFDFD"))
    }

}