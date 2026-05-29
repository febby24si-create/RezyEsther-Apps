package com.example.rezy_esther

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.rezy_esther.databinding.FragmentAboutBinding
import com.example.rezy_esther.databinding.ItemFaqBinding
import com.google.android.material.snackbar.Snackbar

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    // ✅ Data FAQ SiPedes — Named arguments removed because FaqModel is defined in Java
    private val faqList = listOf(
        FaqModel(
            "Apa itu SiPedes?",
            "SiPedes (Sistem Informasi Pembangunan Desa) adalah platform digital terintegrasi untuk mendukung digitalisasi desa Indonesia dalam program Bina Desa Digital.",
            "🏡"
        ),
        FaqModel(
            "Siapa yang dapat menggunakan SiPedes?",
            "SiPedes dapat digunakan oleh perangkat desa, warga desa, mahasiswa, dan pihak terkait program pembangunan desa digital di seluruh Indonesia.",
            "👥"
        ),
        FaqModel(
            "Apa saja fitur utama di aplikasi ini?",
            "Fitur meliputi: Rumus Bangun Datar & Ruang, Program Desa Digital, Potensi Desa (wisata & UMKM), dan akses Website SiPedes secara online.",
            "⚙️"
        ),
        FaqModel(
            "Bagaimana cara mengakses Program Desa?",
            "Buka menu utama di halaman Home, lalu pilih kartu 'Program Desa'. Anda akan melihat daftar program digitalisasi yang sedang berjalan.",
            "🗺️"
        ),
        FaqModel(
            "Apakah SiPedes tersedia offline?",
            "Sebagian fitur seperti Rumus Bangun tersedia offline. Fitur Website dan konten online memerlukan koneksi internet yang stabil.",
            "📶"
        ),
        FaqModel(
            "Bagaimana cara melaporkan bug atau masalah?",
            "Hubungi tim pengembang melalui email yang tertera di halaman Profile aplikasi. Sebutkan detail masalah dan langkah reproduksinya.",
            "🐛"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup Toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "About"
            setDisplayShowTitleEnabled(true)
        }

        // Render FAQ sebagai view manual di dalam LinearLayout container
        setupFaqList()

        // Animasi
        val fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        binding.root.startAnimation(fadeIn)
    }

    private fun setupFaqList() {
        binding.llFaqContainer.removeAllViews()

        faqList.forEachIndexed { index, faq ->
            val itemBinding = ItemFaqBinding.inflate(
                LayoutInflater.from(requireContext()),
                binding.llFaqContainer,
                false
            )

            itemBinding.tvFaqIcon.text       = faq.iconEmoji
            itemBinding.tvFaqPertanyaan.text = faq.pertanyaan
            itemBinding.tvFaqJawaban.text    = faq.jawaban

            // Sembunyikan jawaban awal
            itemBinding.tvFaqJawaban.visibility = View.GONE
            itemBinding.ivFaqArrow.rotation     = -90f

            // Toggle expand/collapse per item
            itemBinding.root.setOnClickListener {
                val isExpanded = itemBinding.tvFaqJawaban.visibility == View.VISIBLE
                if (isExpanded) {
                    itemBinding.tvFaqJawaban.visibility = View.GONE
                    itemBinding.ivFaqArrow.animate()
                        .rotation(-90f).setDuration(200).start()
                } else {
                    itemBinding.tvFaqJawaban.visibility = View.VISIBLE
                    itemBinding.ivFaqArrow.animate()
                        .rotation(90f).setDuration(200).start()

                    // Snackbar info singkat
                    Snackbar.make(
                        binding.root,
                        "FAQ ${index + 1} dari ${faqList.size}",
                        Snackbar.LENGTH_SHORT
                    ).setBackgroundTint(requireContext().getColor(R.color.primaryGreen))
                        .setTextColor(requireContext().getColor(R.color.white))
                        .show()
                }
            }

            binding.llFaqContainer.addView(itemBinding.root)

            // Divider antar item (kecuali yang terakhir)
            if (index < faqList.size - 1) {
                val divider = View(requireContext()).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 1
                    )
                    setBackgroundColor(requireContext().getColor(R.color.dividerColor))
                }
                binding.llFaqContainer.addView(divider)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
