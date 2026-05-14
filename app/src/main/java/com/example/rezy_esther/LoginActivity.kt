package com.example.rezy_esther

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rezy_esther.R
import com.example.rezy_esther.databinding.ActivityLoginBinding
import com.example.rezy_esther.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    companion object {
        private const val VALID_USERNAME = "admin"
        private const val VALID_PASSWORD = "123"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAnimations()
        setupClickListeners()
    }

    private fun setupAnimations() {
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        binding.loginCard.startAnimation(slideUp)
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isEmpty()) {
                binding.tilUsername.error = "Username tidak boleh kosong"
                return@setOnClickListener
            } else {
                binding.tilUsername.error = null
            }

            if (password.isEmpty()) {
                binding.tilPassword.error = "Password tidak boleh kosong"
                return@setOnClickListener
            } else {
                binding.tilPassword.error = null
            }

            if (username == VALID_USERNAME && password == VALID_PASSWORD) {
                doLogin()
            } else {
                Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show()
                // Shake animation on card
                binding.loginCard.animate()
                    .translationX(16f).setDuration(60)
                    .withEndAction {
                        binding.loginCard.animate()
                            .translationX(-16f).setDuration(60)
                            .withEndAction {
                                binding.loginCard.animate()
                                    .translationX(8f).setDuration(60)
                                    .withEndAction {
                                        binding.loginCard.animate()
                                            .translationX(0f).setDuration(60)
                                            .start()
                                    }.start()
                            }.start()
                    }.start()
            }
        }
    }

    private fun doLogin() {
        // Save login state
        val sharedPref = getSharedPreferences("LOGIN_PREF", MODE_PRIVATE)
        sharedPref.edit().putBoolean("isLogin", true).apply()

        // Button loading effect
        binding.btnLogin.isEnabled = false
        binding.btnLogin.text = "Memuat..."

        binding.loginCard.animate()
            .alpha(0f)
            .setDuration(300)
            .withEndAction {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
            .start()
    }
}
