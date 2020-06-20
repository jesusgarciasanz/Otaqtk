package com.example.otaqtk.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.otaqtk.R
import com.example.otaqtk.databinding.FragmentNotRegisteredBinding
import com.example.otaqtk.ui.login.LoginActivity
import com.example.otaqtk.ui.register.RegisterActivity

class NotRegisteredFragment : Fragment() {

    private lateinit var binding: FragmentNotRegisteredBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_not_registered, container, false)

        initListeners()
        return binding.root
    }

    private fun initListeners() {
        binding.registerButtonAnonimous.setOnClickListener {
            val intent = Intent(activity, RegisterActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            activity?.finish()
        }
        binding.loginButtonAnonimous.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            activity?.finish()
        }
    }
}