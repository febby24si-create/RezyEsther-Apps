package com.example.rezy_esther

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rezy_esther.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userPref: android.content.SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi SharedPreferences untuk data user
        userPref = getSharedPreferences("USER_DATA_PREF", MODE_PRIVATE)

        // Cek apakah sudah login sebelumnya
        val loginPref = getSharedPreferences("LOGIN_PREF", MODE_PRIVATE)
        if (loginPref.getBoolean("isLogin", false)) {
            navigateToMain()
            return
        }

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

            // Reset errors
            binding.tilUsername.error = null
            binding.tilPassword.error = null

            // Validasi kosong
            if (username.isEmpty()) {
                binding.tilUsername.error = "Username tidak boleh kosong"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.tilPassword.error = "Password tidak boleh kosong"
                return@setOnClickListener
            }

            // ===== SOAL a3: LOGIN DENGAN 2 RULE =====
            when {
                // RULE 1: Username = Password (admin = 123 tidak masuk sini, karena admin != 123)
                username == password -> {
                    Toast.makeText(this, "Login berhasil (Rule 1: username = password)", Toast.LENGTH_SHORT).show()
                    doLogin(username)
                }
                // RULE 2: Cek ke SharedPreferences user terdaftar
                isValidUserFromPref(username, password) -> {
                    Toast.makeText(this, "Login berhasil (Rule 2: user terdaftar)", Toast.LENGTH_SHORT).show()
                    doLogin(username)
                }
                // FALLBACK: Admin default (untuk testing awal sebelum ada user registrasi)
                username == "admin" && password == "123" -> {
                    Toast.makeText(this, "Login berhasil (Admin default)", Toast.LENGTH_SHORT).show()
                    doLogin(username)
                }
                else -> {
                    Toast.makeText(this, "Username atau password salah!", Toast.LENGTH_SHORT).show()
                    showShakeAnimation()
                }
            }
        }

        // Tombol Register
        binding.tvRegisterLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    // ===== SOAL a3: CEK USER DARI SHAREDPREFERENCES =====
    private fun isValidUserFromPref(username: String, password: String): Boolean {
        // Ambil data user berdasarkan username
        val userData = userPref.getString("user_$username", null)

        if (userData != null) {
            // Format: "nama|tanggalLahir|jenisKelamin|agama|password"
            val parts = userData.split("|")
            if (parts.size == 5) {
                // parts[4] adalah password
                return parts[4] == password
            }
        }
        return false
    }

    private fun showShakeAnimation() {
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

    private fun doLogin(username: String) {
        // Save login state
        val loginPref = getSharedPreferences("LOGIN_PREF", MODE_PRIVATE)
        loginPref.edit()
            .putBoolean("isLogin", true)
            .putString("currentUser", username)
            .apply()

        navigateToMain()
    }

    private fun navigateToMain() {
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