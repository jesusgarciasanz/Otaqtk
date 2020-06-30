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
import com.example.otaqtk.ui.edit_profile.EditProfileActivity
import com.example.otaqtk.ui.profile_settings.SettingsActivity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.otaqtk.api.OtaqtkRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {
    private val otaqtkRepository: OtaqtkRepository = OtaqtkRepository()
    private lateinit var binding: FragmentProfileBinding
    private lateinit var token: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        val value: SharedPreferences? = activity!!.getSharedPreferences("user_token", Context.MODE_PRIVATE )
        token = value?.getString("token", "").toString()

        getProfile()
        initListeners()
        return binding.root
    }

    private fun initListeners() {
        binding.buttonSetting.setOnClickListener {
            startActivity(Intent(activity, SettingsActivity::class.java))
        }

        binding.buttonEditProfile.setOnClickListener {
            val intent = Intent(activity, EditProfileActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    private fun getProfile(){
        CoroutineScope(Dispatchers.Main).launch {
            val call = otaqtkRepository.getProfile(FirebaseAuth.getInstance().uid!!, token)
            val profile = call.body()
            if (profile != null && call.code() == 200){
                binding.profileName.text = profile.username
                Picasso.get().load(profile.profile_image).fit().centerCrop().into(binding.profileImage)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK ){
            getProfile()
        }
    }
}