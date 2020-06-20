package com.example.otaqtk.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.otaqtk.R
import com.example.otaqtk.databinding.FragmentProfileBinding
import com.example.otaqtk.ui.profile_settings.SettingsActivity

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        initListeners()
        return binding.root
    }

    private fun initListeners() {
        binding.buttonSetting.setOnClickListener {
            startActivity(Intent(activity, SettingsActivity::class.java))
        }
    }
}