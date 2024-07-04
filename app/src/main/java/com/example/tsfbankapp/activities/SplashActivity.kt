package com.example.tsfbankapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.example.tsfbankapp.R
import com.example.tsfbankapp.databinding.ActivitySplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity  : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide)
        binding.ivLogo.startAnimation(sideAnimation)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        },3000)
    }
}