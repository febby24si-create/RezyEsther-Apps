package com.example.rezy_esther.pertemuan_10

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rezy_esther.databinding.FragmentUmkmBinding

/**
 * UmkmFragment — Tab "UMKM"
 * Menampilkan daftar produk/usaha UMKM warga desa dalam grid 2 kolom
 */
class UmkmFragment : Fragment() {

    private var _binding: FragmentUmkmBinding? = null
    private val binding get() = _binding!!

    // =====================================================
    // DATA — 20 produk UMKM khas desa (relevan tema SiPedes)
    // =====================================================
    private val umkmList = listOf(
        UmkmModel("Keripik Singkong Pedas",   "Ibu Sumiati",   "🍟 Kuliner",    "Rp 15.000 / bks",  "https://picsum.photos/seed/keripik1/400/300"),
        UmkmModel("Kopi Robusta Desa",        "Pak Hendra",    "☕ Minuman",     "Rp 35.000 / 250g", "https://picsum.photos/seed/kopi1/400/300"),
        UmkmModel("Batik Tulis Tradisional",  "Ibu Kartini",   "🎨 Kerajinan",  "Rp 280.000 / lbr", "https://picsum.photos/seed/batik1/400/300"),
        UmkmModel("Beras Organik Premium",    "Kelompok Tani", "🌾 Pertanian",  "Rp 18.000 / kg",   "https://picsum.photos/seed/beras1/400/300"),
        UmkmModel("Madu Hutan Asli",          "Pak Slamet",    "🍯 Peternakan", "Rp 95.000 / 300ml","https://picsum.photos/seed/madu1/400/300"),
        UmkmModel("Anyaman Bambu",            "Ibu Sari",      "🎋 Kerajinan",  "Rp 75.000 / pcs",  "https://picsum.photos/seed/anyaman1/400/300"),
        UmkmModel("Tempe Kedelai Lokal",      "Pak Wawan",     "🍱 Kuliner",    "Rp 5.000 / papan", "https://picsum.photos/seed/tempe1/400/300"),
        UmkmModel("Pupuk Kompos Organik",     "Gapoktan Maju", "🌱 Pertanian",  "Rp 12.000 / kg",   "https://picsum.photos/seed/pupuk1/400/300"),
        UmkmModel("Gula Aren Cetak",          "Ibu Nurhasanah","🍬 Pangan",     "Rp 25.000 / kg",   "https://picsum.photos/seed/gula1/400/300"),
        UmkmModel("Jamu Herbal Tradisional",  "Ibu Yanti",     "🌿 Kesehatan",  "Rp 20.000 / btl",  "https://picsum.photos/seed/jamu1/400/300"),
        UmkmModel("Abon Ikan Sungai",         "Pak Rudi",      "🐟 Perikanan",  "Rp 55.000 / 200g", "https://picsum.photos/seed/abon1/400/300"),
        UmkmModel("Tas Rotan Handmade",       "Ibu Marlina",   "👜 Kerajinan",  "Rp 185.000 / pcs", "https://picsum.photos/seed/rotan1/400/300"),
        UmkmModel("Dendeng Sapi Lokal",       "Pak Agus",      "🥩 Kuliner",    "Rp 120.000 / 250g","https://picsum.photos/seed/dendeng1/400/300"),
        UmkmModel("Bibit Sayuran Unggul",     "Ibu Fatimah",   "🥬 Pertanian",  "Rp 8.000 / sachet","https://picsum.photos/seed/bibit1/400/300"),
        UmkmModel("Kerajinan Gerabah",        "Pak Darmawan",  "🏺 Kerajinan",  "Rp 65.000 / pcs",  "https://picsum.photos/seed/gerabah1/400/300"),
        UmkmModel("Sambal Terasi Khas",       "Ibu Dewi",      "🌶️ Kuliner",   "Rp 22.000 / jar",  "https://picsum.photos/seed/sambal1/400/300"),
        UmkmModel("Kayu Ukir Dekoratif",      "Pak Bambang",   "🪵 Kerajinan",  "Rp 350.000 / pcs", "https://picsum.photos/seed/ukir1/400/300"),
        UmkmModel("Telur Ayam Kampung",       "Ibu Sunarti",   "🥚 Peternakan", "Rp 3.000 / butir", "https://picsum.photos/seed/telur1/400/300"),
        UmkmModel("Sirup Markisa Segar",      "Ibu Rahayu",    "🥤 Minuman",    "Rp 30.000 / btl",  "https://picsum.photos/seed/sirup1/400/300"),
        UmkmModel("Baju Sulam Khas Desa",     "Ibu Aminah",    "👗 Fashion",    "Rp 175.000 / pcs", "https://picsum.photos/seed/sulam1/400/300")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUmkmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val adapter = UmkmAdapter(umkmList) { selectedItem ->
            // Aksi ketika item diklik
            Toast.makeText(
                requireContext(),
                "📦 ${selectedItem.namaUsaha} — ${selectedItem.pemilik}",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.rvUmkm.apply {
            // Grid 2 kolom — lebih menarik dibanding list linear
            layoutManager = GridLayoutManager(requireContext(), 2)
            this.adapter  = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}