package com.example.rezy_esther

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rezy_esther.R
import com.example.rezy_esther.databinding.ActivityRumusBinding

class RumusActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRumusBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRumusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        animateCards()
        setupCalculators()
    }

    private fun animateCards() {
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        binding.cardSegitiga.startAnimation(slideUp)
        binding.cardKubus.postDelayed({
            binding.cardKubus.startAnimation(slideUp)
        }, 150)
    }

    private fun setupCalculators() {
        // ── Segitiga ──
        binding.btnHitungSegitiga.setOnClickListener {
            val alasStr = binding.etAlas.text.toString().trim()
            val tinggiStr = binding.etTinggi.text.toString().trim()

            if (alasStr.isEmpty() || tinggiStr.isEmpty()) {
                Toast.makeText(this, "Isi alas dan tinggi terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val alas = alasStr.toDoubleOrNull()
            val tinggi = tinggiStr.toDoubleOrNull()

            if (alas == null || tinggi == null || alas <= 0 || tinggi <= 0) {
                Toast.makeText(this, "Masukkan angka yang valid dan lebih dari 0", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val luas = 0.5 * alas * tinggi
            val formatted = if (luas == luas.toLong().toDouble()) {
                "Luas = ${luas.toLong()} cm²"
            } else {
                "Luas = ${"%.2f".format(luas)} cm²"
            }

            binding.tvHasilSegitiga.text = formatted
            binding.cardHasilSegitiga.visibility = View.VISIBLE

            val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
            binding.cardHasilSegitiga.startAnimation(fadeIn)

            // Button feedback
            binding.btnHitungSegitiga.text = "✓ Dihitung"
            binding.btnHitungSegitiga.postDelayed({
                binding.btnHitungSegitiga.text = "Hitung Luas"
            }, 1500)
        }

        // ── Kubus ──
        binding.btnHitungKubus.setOnClickListener {
            val sisiStr = binding.etSisi.text.toString().trim()

            if (sisiStr.isEmpty()) {
                Toast.makeText(this, "Isi panjang sisi terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sisi = sisiStr.toDoubleOrNull()

            if (sisi == null || sisi <= 0) {
                Toast.makeText(this, "Masukkan angka yang valid dan lebih dari 0", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val volume = sisi * sisi * sisi
            val formatted = if (volume == volume.toLong().toDouble()) {
                "Volume = ${volume.toLong()} cm³"
            } else {
                "Volume = ${"%.2f".format(volume)} cm³"
            }

            binding.tvHasilKubus.text = formatted
            binding.cardHasilKubus.visibility = View.VISIBLE

            val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
            binding.cardHasilKubus.startAnimation(fadeIn)

            // Button feedback
            binding.btnHitungKubus.text = "✓ Dihitung"
            binding.btnHitungKubus.postDelayed({
                binding.btnHitungKubus.text = "Hitung Volume"
            }, 1500)
        }
    }
}
