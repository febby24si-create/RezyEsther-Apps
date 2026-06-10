package com.example.rezy_esther.onboarding

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rezy_esther.databinding.ItemOnboardingBinding

class OnBoardingAdapter(
    private val items: List<OnBoardingItem>
) : RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {

    inner class OnBoardingViewHolder(
        val binding: ItemOnboardingBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        val binding = ItemOnboardingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OnBoardingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        val item = items[position]
        val b    = holder.binding

        // Isi konten
        b.tvOnboardTitle.text       = item.title
        b.tvOnboardDescription.text = item.description
        b.ivOnboardIllustration.setImageResource(item.iconRes)

        // Warna background per slide
        try {
            b.onboardItemRoot.setBackgroundColor(
                android.graphics.Color.parseColor(item.backgroundColor)
            )
        } catch (e: Exception) {
            b.onboardItemRoot.setBackgroundColor(
                android.graphics.Color.parseColor("#F0F7F0")
            )
        }

        // Animasi masuk
        val set = AnimatorSet()
        set.playTogether(
            ObjectAnimator.ofFloat(b.ivOnboardIllustration, "alpha", 0f, 1f).apply { duration = 600 },
            ObjectAnimator.ofFloat(b.tvOnboardTitle, "alpha", 0f, 1f).apply { duration = 500 },
            ObjectAnimator.ofFloat(b.tvOnboardTitle, "translationY", 80f, 0f).apply { duration = 500 },
            ObjectAnimator.ofFloat(b.tvOnboardDescription, "alpha", 0f, 1f).apply { duration = 500; startDelay = 150 },
            ObjectAnimator.ofFloat(b.tvOnboardDescription, "translationY", 60f, 0f).apply { duration = 500; startDelay = 150 }
        )
        set.start()
    }

    override fun getItemCount(): Int = items.size
}