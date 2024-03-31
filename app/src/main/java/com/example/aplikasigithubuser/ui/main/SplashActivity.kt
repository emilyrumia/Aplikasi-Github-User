package com.example.aplikasigithubuser.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikasigithubuser.R

class SplashActivity : AppCompatActivity() {

    private val splashTimeout: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, splashTimeout)
    }
}