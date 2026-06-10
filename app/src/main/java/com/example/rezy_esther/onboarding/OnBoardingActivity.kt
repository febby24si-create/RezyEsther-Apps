package com.example.rezy_esther.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.rezy_esther.LoginActivity
import com.example.rezy_esther.R
import com.example.rezy_esther.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding
    private lateinit var adapter: OnBoardingAdapter

    // ✅ Daftar halaman OnBoarding — sesuai tema SiPedes
    private val onBoardingItems = listOf(
        OnBoardingItem(
            iconRes = R.drawable.ic_village_illustration,
            title = "Selamat Datang di SiPedes",
            description = "Platform digital untuk kemajuan dan pemberdayaan desa Indonesia. Kelola informasi desa dengan mudah dan modern.",
            backgroundColor = "#F0F7F0"
        ),
        OnBoardingItem(
            iconRes = R.drawable.ic_program_illustration,
            title = "Fitur Lengkap untuk Desa",
            description = "Akses Program Desa, Potensi Wisata, UMKM, Direktori Warga, Rumus Perhitungan, dan layanan website resmi desa dalam satu genggaman.",
            backgroundColor = "#E8F5E9"
        ),
        OnBoardingItem(
            iconRes = R.drawable.ic_potensi_illustration,
            title = "Berita & Informasi Terkini",
            description = "Tetap update dengan berita dan informasi terbaru seputar desa dan pemerintahan Indonesia langsung dari sumber terpercaya.",
            backgroundColor = "#F1F8E9"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapter()
        setupViewPager()
        setupClickListeners()
    }

    private fun setupAdapter() {
        adapter = OnBoardingAdapter(onBoardingItems)
        binding.viewPagerOnBoarding.adapter = adapter
    }

    private fun setupViewPager() {
        // Hubungkan DotsIndicator dengan ViewPager2
        binding.dotsIndicator.attachTo(binding.viewPagerOnBoarding)

        binding.viewPagerOnBoarding.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateButtonState(position)
            }
        })
    }

    private fun updateButtonState(position: Int) {
        val isLastPage = position == onBoardingItems.size - 1

        if (isLastPage) {
            // Halaman terakhir: tampilkan "Ayo Mulai", sembunyikan Next
            binding.btnNext.visibility = View.GONE
            binding.btnAyoMulai.visibility = View.VISIBLE
            binding.btnSkip.visibility = View.GONE
        } else {
            // Halaman awal/tengah: tampilkan Next & Skip
            binding.btnNext.visibility = View.VISIBLE
            binding.btnAyoMulai.visibility = View.GONE
            binding.btnSkip.visibility = View.VISIBLE
        }
    }

    private fun setupClickListeners() {
        // Tombol Next — pindah ke halaman berikutnya
        binding.btnNext.setOnClickListener {
            val nextPage = binding.viewPagerOnBoarding.currentItem + 1
            if (nextPage < onBoardingItems.size) {
                binding.viewPagerOnBoarding.currentItem = nextPage
            }
        }

        // Tombol Skip — lewati ke Login langsung
        binding.btnSkip.setOnClickListener {
            navigateToLogin()
        }

        // Tombol "Ayo Mulai" — simpan status OnBoarding & ke Login
        binding.btnAyoMulai.setOnClickListener {
            navigateToLogin()
        }
    }

    // ✅ Simpan status onboarding & navigasi ke Login
    private fun navigateToLogin() {
        // Tandai onboarding sudah selesai
        val pref = getSharedPreferences("ONBOARDING_PREF", MODE_PRIVATE)
        pref.edit().putBoolean("isOnBoardingDone", true).apply()

        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}