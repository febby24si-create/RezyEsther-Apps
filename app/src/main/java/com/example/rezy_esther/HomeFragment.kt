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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rezy_esther.data.repository.NewsRepository
import com.example.rezy_esther.databinding.FragmentHomeBinding
import com.example.rezy_esther.home.news.NewsAdapter
import com.google.android.material.chip.Chip
import com.example.rezy_esther.pertemuan_10.TenthActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Repository berita
    private val newsRepository = NewsRepository()

    // Adapter dibuat sekali, list dikosongkan dulu
    private val newsAdapter = NewsAdapter { article ->
        // Klik artikel → buka URL di WebViewActivity
        val intent = Intent(requireContext(), WebViewActivity::class.java).apply {
            putExtra("NEWS_URL",   article.url)
            putExtra("NEWS_TITLE", article.title)
        }
        startActivity(intent)
        requireActivity().overridePendingTransition(
            android.R.anim.fade_in, android.R.anim.fade_out
        )
    }

    // ─── Lifecycle ────────────────────────────────────────────────────────────

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

        // Toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Dashboard"
            setDisplayShowTitleEnabled(true)
        }

        animateCards()
        setupChipFilter()
        setupClicks()

        // ✅ Berita
        setupNewsRecyclerView()
        loadNews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null   // cegah memory leak
    }

    // ─── Berita ───────────────────────────────────────────────────────────────

    private fun setupNewsRecyclerView() {
        binding.rvBerita.apply {
            adapter        = newsAdapter
            layoutManager  = LinearLayoutManager(requireContext())
            // penting: false agar NestedScrollView yang scroll, bukan RV sendiri
            isNestedScrollingEnabled = false
        }
    }

    private fun loadNews() {
        showLoading()

        lifecycleScope.launch {
            newsRepository.getTopHeadlines()
                .onSuccess { articles ->
                    if (_binding == null) return@onSuccess   // fragment sudah destroyed
                    if (articles.isEmpty()) {
                        showEmpty()
                    } else {
                        newsAdapter.submitList(articles)
                        showContent()
                    }
                }
                .onFailure { error ->
                    if (_binding == null) return@onFailure
                    showError(error.message ?: "Tidak dapat memuat berita")
                }
        }
    }

    // ─── State helpers ────────────────────────────────────────────────────────

    private fun showLoading() {
        binding.shimmerNews.visibility  = View.VISIBLE
        binding.rvBerita.visibility     = View.GONE
        binding.tvNewsError.visibility  = View.GONE
        binding.tvNewsEmpty.visibility  = View.GONE
    }

    private fun showContent() {
        binding.shimmerNews.visibility  = View.GONE
        binding.rvBerita.visibility     = View.VISIBLE
        binding.tvNewsError.visibility  = View.GONE
        binding.tvNewsEmpty.visibility  = View.GONE
    }

    private fun showError(msg: String) {
        binding.shimmerNews.visibility = View.GONE
        binding.rvBerita.visibility    = View.GONE
        binding.tvNewsEmpty.visibility = View.GONE
        binding.tvNewsError.visibility = View.VISIBLE
        // Set pesan error ke TextView tvNewsError langsung
        binding.tvNewsError.text = "⚠️ $msg"
    }

    private fun showEmpty() {
        binding.shimmerNews.visibility  = View.GONE
        binding.rvBerita.visibility     = View.GONE
        binding.tvNewsError.visibility  = View.GONE
        binding.tvNewsEmpty.visibility  = View.VISIBLE
    }

    // ─── Chip filter (tidak diubah) ───────────────────────────────────────────

    private fun setupChipFilter() {
        binding.chipGroupFilter.setOnCheckedStateChangeListener { group, checkedIds ->
            val selectedId = checkedIds.firstOrNull() ?: return@setOnCheckedStateChangeListener
            val chip = group.findViewById<Chip>(selectedId)
            val category = chip.text.toString()
                .replace(Regex("[^\\p{L}\\p{N}\\s]"), "").trim()

            val descMap = mapOf(
                "Semua"       to "Pilih layanan yang tersedia",
                "Perhitungan" to "Menampilkan fitur perhitungan",
                "Program"     to "Menampilkan program desa",
                "Potensi"     to "Menampilkan potensi desa",
                "Online"      to "Menampilkan layanan online"
            )
            binding.tvFilterDesc.text = descMap[category] ?: "Pilih layanan yang tersedia"
            applyMenuFilter(category)
        }
    }

    private fun applyMenuFilter(category: String) {
        val all = category == "Semua"
        binding.rowMenu1.visibility  = if (all || category == "Perhitungan" || category == "Program") View.VISIBLE else View.GONE
        binding.rowMenu2.visibility  = if (all || category == "Potensi"     || category == "Online")  View.VISIBLE else View.GONE
        binding.cardRumus.visibility   = if (all || category == "Perhitungan") View.VISIBLE else View.GONE
        binding.cardProgram.visibility = if (all || category == "Program")     View.VISIBLE else View.GONE
        binding.cardPotensi.visibility = if (all || category == "Potensi")     View.VISIBLE else View.GONE
        binding.cardWebsite.visibility = if (all || category == "Online")      View.VISIBLE else View.GONE
    }

    // ─── Card animasi & klik (tidak diubah) ──────────────────────────────────

    private fun animateCards() {
        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)
        listOf(
            binding.cardRumus, binding.cardProgram,
            binding.cardPotensi, binding.cardWebsite,
            binding.cardDirektori, binding.cardLogout
        ).forEachIndexed { i, card ->
            card.postDelayed({ card.startAnimation(anim) }, i * 80L)
        }
    }

    private fun setupClicks() {
        fun launch(cls: Class<*>) {
            startActivity(Intent(requireContext(), cls))
            requireActivity().overridePendingTransition(
                android.R.anim.fade_in, android.R.anim.fade_out
            )
        }
        fun CardView(v: View, action: () -> Unit) {
            v.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction {
                v.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
                action()
            }.start()
        }

        binding.cardRumus.setOnClickListener    { CardView(it) { launch(RumusActivity::class.java) } }
        binding.cardProgram.setOnClickListener  { CardView(it) { launch(ProgramDesaActivity::class.java) } }
        binding.cardPotensi.setOnClickListener  { CardView(it) { launch(PotensiDesaActivity::class.java) } }
        binding.cardWebsite.setOnClickListener  { CardView(it) { launch(WebViewActivity::class.java) } }
        binding.cardDirektori.setOnClickListener { CardView(it) { launch(TenthActivity::class.java) } }
        binding.cardLogout.setOnClickListener   { showLogoutDialog() }
    }

    // ─── Logout (tidak diubah) ────────────────────────────────────────────────

    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.logout_confirm_title))
            .setMessage(getString(R.string.logout_confirm_msg))
            .setPositiveButton(getString(R.string.yes)) { _, _ -> doLogout() }
            .setNegativeButton(getString(R.string.no)) { d, _ ->
                d.dismiss()
                Snackbar.make(binding.root, getString(R.string.logout_cancelled), Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(requireContext().getColor(R.color.primaryGreen))
                    .setTextColor(requireContext().getColor(R.color.white))
                    .show()
            }
            .setCancelable(true).show()
    }

    private fun doLogout() {
        requireContext()
            .getSharedPreferences("LOGIN_PREF", AppCompatActivity.MODE_PRIVATE)
            .edit().clear().apply()

        startActivity(
            Intent(requireContext(), LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        )
        requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        requireActivity().finish()
    }
}
