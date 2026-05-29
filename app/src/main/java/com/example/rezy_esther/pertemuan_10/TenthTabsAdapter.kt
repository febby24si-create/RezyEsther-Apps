package com.example.rezy_esther.pertemuan_10

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TenthTabsAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> InfrastrukturFragment()
            1 -> UmkmFragment()
            2 -> SdmFragment()
            else -> throw IllegalStateException("Posisi tab tidak valid: $position")
        }
    }
}