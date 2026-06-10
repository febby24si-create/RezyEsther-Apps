package com.example.rezy_esther

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.rezy_esther.databinding.ActivitySplashScreenBinding
import com.example.rezy_esther.LoginActivity
import com.example.rezy_esther.MainActivity
import com.example.rezy_esther.onboarding.OnBoardingActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Mengaktifkan AndroidX Splash Screen API sebelum super.onCreate
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menutup splash bawaan sistem dengan cepat karena kita pakai custom animasi di XML
        splashScreen.setKeepOnScreenCondition { false }

        startAnimations()
        navigateAfterDelay()
    }

    private fun startAnimations() {
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        // Animasi container logo utama
        binding.logoContainer.startAnimation(fadeIn)
        binding.logoContainer.visibility = View.VISIBLE

        // Animasi loading dengan delay 800ms
        Handler(Looper.getMainLooper()).postDelayed({
            binding.loadingContainer.animate()
                .alpha(1f)
                .setDuration(600)
                .start()
        }, 800)

        // Animasi denyut (pulse) pada logo
        binding.logoContainer.animate()
            .scaleX(1.05f)
            .scaleY(1.05f)
            .setDuration(800)
            .withEndAction {
                binding.logoContainer.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(800)
                    .start()
            }
            .start()
    }

    private fun navigateAfterDelay() {
        Handler(Looper.getMainLooper()).postDelayed({

            // ✅ CEK 1: Apakah sudah login?
            val loginPref = getSharedPreferences("LOGIN_PREF", MODE_PRIVATE)
            val isLogin = loginPref.getBoolean("isLogin", false)

            // ✅ CEK 2: Apakah onboarding sudah pernah ditampilkan?
            val onBoardPref = getSharedPreferences("ONBOARDING_PREF", MODE_PRIVATE)
            val isOnBoardingDone = onBoardPref.getBoolean("isOnBoardingDone", false)

            // ✅ ALUR NAVIGASI:
            // Login → MainActivity
            // Belum login + OnBoarding sudah → LoginActivity
            // Belum login + OnBoarding belum → OnBoardingActivity
            val intent = when {
                isLogin -> Intent(this, MainActivity::class.java)
                isOnBoardingDone -> Intent(this, LoginActivity::class.java)
                else -> Intent(this, OnBoardingActivity::class.java)
            }

            binding.splashRoot.animate()
                .alpha(0f).setDuration(400)
                .withEndAction {
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                }.start()

        }, 2500)
    }
}