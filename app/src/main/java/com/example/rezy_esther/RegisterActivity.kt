package com.example.rezy_esther

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rezy_esther.databinding.ActivityRegisterBinding
import com.example.rezy_esther.UserData
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var sharedPref: android.content.SharedPreferences

    companion object {
        private const val PREF_NAME = "USER_DATA_PREF"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE)

        setupAnimations()
        setupDatePicker()
        setupAgamaDropdown()
        setupClickListeners()
    }

    private fun setupAnimations() {
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        binding.registerCard.startAnimation(slideUp)
    }

    private fun setupDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        binding.etTanggalLahir.setOnClickListener {
            DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val tanggal = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    binding.etTanggalLahir.setText(tanggal)
                },
                year, month, day
            ).show()
        }

        // Juga handle klik pada icon calendar
        binding.tilTanggalLahir.setEndIconOnClickListener {
            DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val tanggal = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    binding.etTanggalLahir.setText(tanggal)
                },
                year, month, day
            ).show()
        }
    }

    private fun setupAgamaDropdown() {
        val agamaOptions = resources.getStringArray(R.array.agama_options)
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, agamaOptions)
        (binding.etAgama as androidx.appcompat.widget.AppCompatAutoCompleteTextView).setAdapter(adapter)
        binding.etAgama.threshold = 1
    }

    private fun setupClickListeners() {
        binding.btnRegister.setOnClickListener {
            performRegistration()
        }

        binding.tvLoginLink.setOnClickListener {
            finish() // Kembali ke LoginActivity
        }
    }

    private fun performRegistration() {
        // Ambil semua nilai input
        val nama = binding.etNama.text.toString().trim()
        val tanggalLahir = binding.etTanggalLahir.text.toString().trim()
        val jenisKelamin = getSelectedJenisKelamin()
        val agama = binding.etAgama.text.toString().trim()
        val username = binding.etUsername.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()

        // Validasi Nama
        if (nama.isEmpty()) {
            binding.tilNama.error = "Nama lengkap tidak boleh kosong"
            return
        } else {
            binding.tilNama.error = null
        }

        // Validasi Tanggal Lahir
        if (tanggalLahir.isEmpty()) {
            binding.tilTanggalLahir.error = "Tanggal lahir harus diisi"
            return
        } else {
            binding.tilTanggalLahir.error = null
        }

        // Validasi Jenis Kelamin
        if (jenisKelamin.isEmpty()) {
            Toast.makeText(this, "Pilih jenis kelamin terlebih dahulu", Toast.LENGTH_SHORT).show()
            return
        }

        // Validasi Agama
        if (agama.isEmpty()) {
            binding.tilAgama.error = "Pilih agama terlebih dahulu"
            return
        } else {
            binding.tilAgama.error = null
        }

        // Validasi Username
        if (username.isEmpty()) {
            binding.tilUsername.error = "Username tidak boleh kosong"
            return
        } else if (username.length < 4) {
            binding.tilUsername.error = "Username minimal 4 karakter"
            return
        } else {
            binding.tilUsername.error = null
        }

        // Cek apakah username sudah terdaftar
        if (isUsernameExists(username)) {
            binding.tilUsername.error = "Username sudah terdaftar, gunakan username lain"
            return
        }

        // Validasi Password
        if (password.isEmpty()) {
            binding.tilPassword.error = "Password tidak boleh kosong"
            return
        } else if (password.length < 6) {
            binding.tilPassword.error = "Password minimal 6 karakter"
            return
        } else {
            binding.tilPassword.error = null
        }

        // Validasi Confirm Password
        if (confirmPassword.isEmpty()) {
            binding.tilConfirmPassword.error = "Konfirmasi password harus diisi"
            return
        } else if (password != confirmPassword) {
            binding.tilConfirmPassword.error = "Password tidak cocok"
            return
        } else {
            binding.tilConfirmPassword.error = null
        }

        // Simpan data user
        saveUserData(
            UserData(
                nama = nama,
                tanggalLahir = tanggalLahir,
                jenisKelamin = jenisKelamin,
                agama = agama,
                username = username,
                password = password
            )
        )

        // Tampilkan dialog sukses
        MaterialAlertDialogBuilder(this)
            .setTitle("Registrasi Berhasil! 🎉")
            .setMessage("Akun Anda telah terdaftar. Silakan login dengan username dan password Anda.")
            .setPositiveButton("Login Sekarang") { _, _ ->
                finish() // Kembali ke LoginActivity
            }
            .show()
    }

    private fun getSelectedJenisKelamin(): String {
        return when {
            binding.rbLaki.isChecked -> "Laki-laki"
            binding.rbPerempuan.isChecked -> "Perempuan"
            else -> ""
        }
    }

    private fun isUsernameExists(username: String): Boolean {
        // Cek apakah username sudah pernah digunakan
        val allUsernames = getAllUsernames()
        return allUsernames.contains(username)
    }

    private fun getAllUsernames(): Set<String> {
        val usernames = mutableSetOf<String>()
        val allEntries = sharedPref.all
        for ((key, value) in allEntries) {
            if (key.startsWith("user_")) {
                // Parse username dari data yang tersimpan
                val userData = value as? String
                userData?.let {
                    // Simpan username dalam format sederhana
                    // Alternatif: kita bisa simpan index username terpisah
                }
            }
        }

        // Cek juga dari login pref untuk user yang sudah login sebelumnya
        val loginPref = getSharedPreferences("LOGIN_PREF", MODE_PRIVATE)
        val savedUsername = loginPref.getString("registered_username", null)
        savedUsername?.let { usernames.add(it) }

        return usernames
    }

    private fun saveUserData(userData: UserData) {
        // Simpan data user dengan key berdasarkan username
        val editor = sharedPref.edit()
        editor.putString("user_${userData.username}",
            "${userData.nama}|${userData.tanggalLahir}|${userData.jenisKelamin}|${userData.agama}|${userData.password}")
        editor.apply()

        // Juga simpan ke LOGIN_PREF untuk pengecekan login nanti
        val loginPref = getSharedPreferences("LOGIN_PREF", MODE_PRIVATE)
        loginPref.edit().putString("registered_username", userData.username).apply()
    }
}