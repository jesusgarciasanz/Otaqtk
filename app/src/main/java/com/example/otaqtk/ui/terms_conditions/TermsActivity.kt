package com.example.otaqtk.ui.terms_conditions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.otaqtk.R
import com.example.otaqtk.databinding.ActivityTermsBinding

class TermsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTermsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_terms )
        binding.lifecycleOwner = this

        initListeners()
    }

    private fun initListeners() {
        binding.buttonBackTerms.setOnClickListener {
            finish()
        }
    }
}