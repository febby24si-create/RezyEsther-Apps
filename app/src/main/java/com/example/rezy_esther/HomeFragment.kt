package com.example.rezy_esther

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.rezy_esther.R
import com.example.rezy_esther.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import com.example.rezy_esther.PotensiDesaActivity
import com.example.rezy_esther.ProgramDesaActivity
import com.example.rezy_esther.RumusActivity
import com.example.rezy_esther.WebViewActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup Toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Dashboard"
            setDisplayShowTitleEnabled(true)
        }

        // Animate cards
        animateCards()

        // Setup click listeners
        setupClicks()
    }

    private fun animateCards() {
        val slideUp = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)
        val cards = listOf(
            binding.cardRumus,
            binding.cardProgram,
            binding.cardPotensi,
            binding.cardWebsite,
            binding.cardLogout
        )
        cards.forEachIndexed { index, card ->
            card.postDelayed({ card.startAnimation(slideUp) }, index * 80L)
        }
    }

    private fun setupClicks() {
        binding.cardRumus.setOnClickListener {
            it.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction {
                it.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
                val intent = Intent(requireContext(), RumusActivity::class.java)
                startActivity(intent)
                requireActivity().overridePendingTransition(
                    android.R.anim.fade_in, android.R.anim.fade_out
                )
            }.start()
        }

        binding.cardProgram.setOnClickListener {
            it.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction {
                it.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
                val intent = Intent(requireContext(), ProgramDesaActivity::class.java)
                startActivity(intent)
                requireActivity().overridePendingTransition(
                    android.R.anim.fade_in, android.R.anim.fade_out
                )
            }.start()
        }

        binding.cardPotensi.setOnClickListener {
            it.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction {
                it.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
                val intent = Intent(requireContext(), PotensiDesaActivity::class.java)
                startActivity(intent)
                requireActivity().overridePendingTransition(
                    android.R.anim.fade_in, android.R.anim.fade_out
                )
            }.start()
        }

        binding.cardWebsite.setOnClickListener {
            it.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction {
                it.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
                val intent = Intent(requireContext(), WebViewActivity::class.java)
                startActivity(intent)
                requireActivity().overridePendingTransition(
                    android.R.anim.fade_in, android.R.anim.fade_out
                )
            }.start()
        }

        binding.cardLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.logout_confirm_title))
            .setMessage(getString(R.string.logout_confirm_msg))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                doLogout()
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
                Snackbar.make(
                    binding.root,
                    getString(R.string.logout_cancelled),
                    Snackbar.LENGTH_SHORT
                ).setBackgroundTint(requireContext().getColor(R.color.primaryGreen))
                    .setTextColor(requireContext().getColor(R.color.white))
                    .show()
            }
            .setCancelable(true)
            .show()
    }

    private fun doLogout() {
        val sharedPref = requireContext().getSharedPreferences("LOGIN_PREF", AppCompatActivity.MODE_PRIVATE)
        sharedPref.edit().clear().apply()

        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
