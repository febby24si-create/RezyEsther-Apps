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
import com.example.rezy_esther.databinding.FragmentHomeBinding
import com.google.android.material.chip.Chip
import com.example.rezy_esther.pertemuan_10.TenthActivity
import com.google.android.material.snackbar.Snackbar
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Mapping chip ke kategori menu
    private val menuCategories = mapOf(
        "Semua"       to listOf("cardRumus", "cardProgram", "cardPotensi", "cardWebsite"),
        "Perhitungan" to listOf("cardRumus"),
        "Program"     to listOf("cardProgram"),
        "Potensi"     to listOf("cardPotensi"),
        "Online"      to listOf("cardWebsite")
    )

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

        // Setup ChipGroup listener
        setupChipFilter()

        // Setup click listeners
        setupClicks()
    }

    private fun setupChipFilter() {
        binding.chipGroupFilter.setOnCheckedStateChangeListener { group, checkedIds ->
            val selectedId = checkedIds.firstOrNull() ?: return@setOnCheckedStateChangeListener
            val chip = group.findViewById<Chip>(selectedId)
            val chipLabel = chip.text.toString()

            // Hapus emoji dari label chip untuk matching
            val category = chipLabel.replace(Regex("[^\\p{L}\\p{N}\\s]"), "").trim()

            // Update deskripsi filter
            val descMap = mapOf(
                "Semua"       to "Pilih layanan yang tersedia",
                "Perhitungan" to "Menampilkan fitur perhitungan",
                "Program"     to "Menampilkan program desa",
                "Potensi"     to "Menampilkan potensi desa",
                "Online"      to "Menampilkan layanan online"
            )
            binding.tvFilterDesc.text = descMap[category] ?: "Pilih layanan yang tersedia"

            // Tampilkan/sembunyikan baris menu sesuai filter
            applyMenuFilter(category)
        }
    }

    private fun applyMenuFilter(category: String) {
        val allVisible = category == "Semua"

        // Map card ke baris (row) yang perlu ditampilkan
        val showRow1 = allVisible ||
                category == "Perhitungan" ||
                category == "Program"

        val showRow2 = allVisible ||
                category == "Potensi" ||
                category == "Online"

        // Tampilkan/sembunyikan card individual
        binding.cardRumus.visibility = if (allVisible || category == "Perhitungan")
            View.VISIBLE else View.GONE

        binding.cardProgram.visibility = if (allVisible || category == "Program")
            View.VISIBLE else View.GONE

        binding.cardPotensi.visibility = if (allVisible || category == "Potensi")
            View.VISIBLE else View.GONE

        binding.cardWebsite.visibility = if (allVisible || category == "Online")
            View.VISIBLE else View.GONE

        // Tampilkan/sembunyikan baris container
        binding.rowMenu1.visibility = if (showRow1) View.VISIBLE else View.GONE
        binding.rowMenu2.visibility = if (showRow2) View.VISIBLE else View.GONE
    }

    private fun animateCards() {
        val slideUp = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)
        val cards = listOf(
            binding.cardRumus,
            binding.cardProgram,
            binding.cardPotensi,
            binding.cardWebsite,
            binding.cardDirektori,  // tambahkan ini
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
                startActivity(Intent(requireContext(), RumusActivity::class.java))
                requireActivity().overridePendingTransition(
                    android.R.anim.fade_in, android.R.anim.fade_out)
            }.start()
        }

        binding.cardProgram.setOnClickListener {
            it.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction {
                it.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
                startActivity(Intent(requireContext(), ProgramDesaActivity::class.java))
                requireActivity().overridePendingTransition(
                    android.R.anim.fade_in, android.R.anim.fade_out)
            }.start()
        }

        binding.cardPotensi.setOnClickListener {
            it.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction {
                it.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
                startActivity(Intent(requireContext(), PotensiDesaActivity::class.java))
                requireActivity().overridePendingTransition(
                    android.R.anim.fade_in, android.R.anim.fade_out)
            }.start()
        }

        binding.cardWebsite.setOnClickListener {
            it.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction {
                it.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
                startActivity(Intent(requireContext(), WebViewActivity::class.java))
                requireActivity().overridePendingTransition(
                    android.R.anim.fade_in, android.R.anim.fade_out)
            }.start()
        }
        binding.cardDirektori.setOnClickListener {
            it.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction {
                it.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
                startActivity(Intent(requireContext(), TenthActivity::class.java))
                requireActivity().overridePendingTransition(
                    android.R.anim.fade_in, android.R.anim.fade_out)
            }.start()
        }
        binding.cardLogout.setOnClickListener { showLogoutDialog() }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.logout_confirm_title))
            .setMessage(getString(R.string.logout_confirm_msg))
            .setPositiveButton(getString(R.string.yes)) { _, _ -> doLogout() }
            .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
                Snackbar.make(binding.root, getString(R.string.logout_cancelled), Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(requireContext().getColor(R.color.primaryGreen))
                    .setTextColor(requireContext().getColor(R.color.white))
                    .show()
            }
            .setCancelable(true)
            .show()
    }

    private fun doLogout() {
        val sharedPref = requireContext().getSharedPreferences(
            "LOGIN_PREF", AppCompatActivity.MODE_PRIVATE)
        sharedPref.edit().clear().apply()

        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().overridePendingTransition(
            android.R.anim.fade_in, android.R.anim.fade_out)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}