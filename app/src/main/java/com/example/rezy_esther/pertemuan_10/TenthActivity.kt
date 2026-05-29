package com.example.rezy_esther.pertemuan_10

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rezy_esther.databinding.ActivityTenthBinding
import com.google.android.material.tabs.TabLayoutMediator

class TenthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTenthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTenthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        setupTabViewPager()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Direktori Desa"
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(com.example.rezy_esther.R.drawable.ic_back)
        }
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setupTabViewPager() {
        val adapter = TenthTabsAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Infrastruktur"
                    tab.setIcon(com.example.rezy_esther.R.drawable.ic_program)
                }
                1 -> {
                    tab.text = "UMKM"
                    tab.setIcon(com.example.rezy_esther.R.drawable.ic_potensi)
                    val badge = tab.orCreateBadge
                    badge.isVisible = true
                    badge.number = 20
                }
                2 -> {
                    tab.text = "SDM"
                    tab.setIcon(com.example.rezy_esther.R.drawable.ic_account)
                    val badge = tab.orCreateBadge
                    badge.isVisible = true
                }
            }
        }.attach()
    }
}