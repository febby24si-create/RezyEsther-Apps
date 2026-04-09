package com.example.rezy_esther

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Segitiga
        val etAlas = findViewById<EditText>(R.id.etAlas)
        val etTinggi = findViewById<EditText>(R.id.etTinggi)
        val btnSegitiga = findViewById<Button>(R.id.btnHitungSegitiga)
        val tvHasilSegitiga = findViewById<TextView>(R.id.tvHasilSegitiga)

        btnSegitiga.setOnClickListener {
            val alas = etAlas.text.toString().toDoubleOrNull()
            val tinggi = etTinggi.text.toString().toDoubleOrNull()

            if (alas != null && tinggi != null) {
                val hasil = 0.5 * alas * tinggi
                tvHasilSegitiga.text = "Hasil: $hasil"
            } else {
                tvHasilSegitiga.text = "Input tidak valid"
            }
        }

        // Balok
        val etPanjang = findViewById<EditText>(R.id.etPanjang)
        val etLebar = findViewById<EditText>(R.id.etLebar)
        val etTinggiBalok = findViewById<EditText>(R.id.etTinggiBalok)
        val btnBalok = findViewById<Button>(R.id.btnHitungBalok)
        val tvHasilBalok = findViewById<TextView>(R.id.tvHasilBalok)

        btnBalok.setOnClickListener {
            val p = etPanjang.text.toString().toDoubleOrNull()
            val l = etLebar.text.toString().toDoubleOrNull()
            val t = etTinggiBalok.text.toString().toDoubleOrNull()

            if (p != null && l != null && t != null) {
                val hasil = p * l * t
                tvHasilBalok.text = "Hasil: $hasil"
            } else {
                tvHasilBalok.text = "Input tidak valid"
            }
        }
    }
}