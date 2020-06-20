package com.example.otaqtk.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.otaqtk.R
import com.example.otaqtk.databinding.ActivityHomeBinding
import com.example.otaqtk.databinding.ActivityRegisterBinding
import com.example.otaqtk.ui.home.HomeActivity
import com.example.otaqtk.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register )
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

    }


    private fun checkRegister(){
        if (binding.editUsernameRegister.text.toString().isEmpty()){
            binding.editUsernameRegister.error =  "Please enter your username"
            binding.editUsernameRegister.requestFocus()
            return
        }
        if (binding.editEmailRegister.text.toString().isEmpty()){
            binding.editEmailRegister.error =  "Please enter your email"
            binding.editEmailRegister.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(binding.editEmailRegister.text.toString()).matches()) {
            binding.editEmailRegister.error = "Please enter a valid email"
            binding.editEmailRegister.requestFocus()
            return
        }

        if (binding.editTextPassword.text.toString().isEmpty()){
            binding.editTextPassword.error = "Please enter a Password"
            binding.editTextPassword.requestFocus()
            return
        }

        if (!binding.editTextPassword.text.toString().equals(binding.editTextConfirmpassword.text.toString())) {
            binding.editTextPassword.error = "Passwords are not equal"
            binding.editTextPassword.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(binding.editEmailRegister.text.toString(), binding.editTextPassword.text.toString())
            .addOnCompleteListener(this){task ->
                if (task.isSuccessful){
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }else{
                    Log.w("FAILED", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this, "SignUp Failed ", Toast.LENGTH_LONG).show()
                }
            }
    }

}