package com.example.otaqtk.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.otaqtk.R
import com.example.otaqtk.databinding.FragmentHomeFragmentBinding
import com.google.android.gms.ads.*


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_fragment,container,false)

        return binding.root
    }

}