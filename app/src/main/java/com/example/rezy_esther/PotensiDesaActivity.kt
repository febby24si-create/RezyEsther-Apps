package com.example.rezy_esther

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.rezy_esther.R
import com.example.rezy_esther.databinding.ActivityPotensiDesaBinding

class PotensiDesaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPotensiDesaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPotensiDesaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.root.startAnimation(fadeIn)
    }
}
