package com.example.rezy_esther

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.rezy_esther.R
import com.example.rezy_esther.databinding.ActivityProgramDesaBinding

class ProgramDesaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProgramDesaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgramDesaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Animate content entrance
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.root.startAnimation(fadeIn)
    }
}
