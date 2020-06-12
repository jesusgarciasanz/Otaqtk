package com.example.otaqtk.ui.profile_settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.otaqtk.R
import com.example.otaqtk.databinding.ActivitySettingsBinding
import com.example.otaqtk.ui.edit_profile.EditProfileActivity
import com.example.otaqtk.ui.login.LoginActivity
import com.example.otaqtk.ui.terms_conditions.TermsActivity
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)
        binding.lifecycleOwner = this

        initListeners()
    }

    private fun initListeners() {
        binding.buttonLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
        binding.accConfigurationButton.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }
        binding.termsConditionsButton.setOnClickListener {
            startActivity(Intent(this, TermsActivity::class.java))
        }
        binding.buttonBackSetting.setOnClickListener {
            finish()
        }
    }
}