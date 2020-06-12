package com.example.otaqtk.ui.splash_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.otaqtk.R
import com.example.otaqtk.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    private val SPLASH_DURATION = 1000
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startLogin()
    }

    private fun startLogin() {
        Handler().postDelayed({
            intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }, SPLASH_DURATION.toLong())
    }
}