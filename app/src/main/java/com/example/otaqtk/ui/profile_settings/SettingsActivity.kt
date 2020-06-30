package com.example.otaqtk.ui.profile_settings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.otaqtk.R
import com.example.otaqtk.api.OtaqtkRepository
import com.example.otaqtk.databinding.ActivitySettingsBinding
import com.example.otaqtk.ui.edit_profile.EditProfileActivity
import com.example.otaqtk.ui.login.LoginActivity
import com.example.otaqtk.ui.terms_conditions.TermsActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private val otaqtkRepository: OtaqtkRepository = OtaqtkRepository()
    private lateinit var token: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)
        binding.lifecycleOwner = this

        val value: SharedPreferences? = getSharedPreferences("user_token", Context.MODE_PRIVATE )
        token = value?.getString("token", "").toString()


        initListeners()
    }

    private fun initListeners() {
        binding.buttonLogOut.setOnClickListener {
            logOut()

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



    private fun logOut(){
        CoroutineScope(Dispatchers.Main).launch {
            val call = otaqtkRepository.logOut(FirebaseAuth.getInstance().currentUser!!.uid, token)
            if ( call.code() == 200){
                val preferences = getSharedPreferences("user_token", Context.MODE_PRIVATE)
                val editor = preferences.edit()
                editor.clear()
                editor.apply()
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this@SettingsActivity, LoginActivity::class.java)
                intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                finish()
            }
        }
    }
}