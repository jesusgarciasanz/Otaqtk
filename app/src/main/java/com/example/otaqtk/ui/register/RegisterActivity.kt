package com.example.otaqtk.ui.register

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings.Secure
import android.text.InputType
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.otaqtk.R
import com.example.otaqtk.api.OtaqtkRepository
import com.example.otaqtk.databinding.ActivityRegisterBinding
import com.example.otaqtk.pojo.Profile
import com.example.otaqtk.ui.home.HomeActivity
import com.example.otaqtk.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding
    private val otaqtkRepository: OtaqtkRepository = OtaqtkRepository()
    private var seePassword: Boolean = false

    @SuppressLint("HardwareIds")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        auth = FirebaseAuth.getInstance()


        initListeners()
    }

    private fun initListeners() {
        binding.buttonCreateAcc.setOnClickListener {
            checkRegister()
        }
        binding.buttonGotoLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.buttonSeePasswordRegister.setOnClickListener {
            if (seePassword == false){
                seePassword = true
                binding.editTextPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.buttonSeePasswordRegister.setImageResource(R.drawable.ic_hide_password)
            }else if (seePassword == true){
                seePassword = false
                binding.editTextPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.buttonSeePasswordRegister.setImageResource(R.drawable.ic_colored_eye)
            }
        }

        binding.seeConfirmPasword.setOnClickListener {
            if (seePassword == false){
                seePassword = true
                binding.editTextConfirmpassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.seeConfirmPasword.setImageResource(R.drawable.ic_hide_password)
            }else if (seePassword == true){
                seePassword = false
                binding.editTextConfirmpassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.seeConfirmPasword.setImageResource(R.drawable.ic_colored_eye)
            }
        }
    }


    private fun checkRegister() {
        if (binding.editUsernameRegister.text.toString().isEmpty()) {
            binding.editUsernameRegister.error = "Please enter your username"
            binding.editUsernameRegister.requestFocus()
            return
        }
        if (binding.editEmailRegister.text.toString().isEmpty()) {
            binding.editEmailRegister.error = "Please enter your email"
            binding.editEmailRegister.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(binding.editEmailRegister.text.toString()).matches()) {
            binding.editEmailRegister.error = "Please enter a valid email"
            binding.editEmailRegister.requestFocus()
            return
        }

        if (binding.editTextPassword.text.toString().isEmpty()) {
            binding.editTextPassword.error = "Please enter a Password"
            binding.editTextPassword.requestFocus()
            return
        }

        if (!binding.editTextPassword.text.toString()
                .equals(binding.editTextConfirmpassword.text.toString())
        ) {
            binding.editTextPassword.error = "Passwords are not equal"
            binding.editTextPassword.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(
            binding.editEmailRegister.text.toString(),
            binding.editTextPassword.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                         createProfile()
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()




                } else {
                    Log.w("FAILED", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this, "SignUp Failed ", Toast.LENGTH_LONG).show()
                }
            }

    }

    private fun createProfile() {
        CoroutineScope(Dispatchers.Main).launch {
            val android_id: String = Secure.getString(contentResolver, Secure.ANDROID_ID)
            val profile = Profile(FirebaseAuth.getInstance().uid, "", android_id, "","",true, binding.editUsernameRegister.text.toString())
            val call = otaqtkRepository.createProfile(profile)
            val token = call.body()
            if (call.code() == 200 ) {
                val pref = getSharedPreferences("user_token",Context.MODE_PRIVATE)
                val editor = pref.edit()
                editor.putString("token", token?.token)
                editor.apply()
            }

        }

    }
}