package com.example.rezy_esther

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.rezy_esther.databinding.FragmentProfileBinding
import com.google.android.material.snackbar.Snackbar

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    // ══════════════════════════════════════════════
    // 🔧 GANTI DATA INI DENGAN DATA KAMU SENDIRI!
    // ══════════════════════════════════════════════
    private val namaLengkap  = "Febby Fahrezy"
    private val nim          = "2457301054"
    private val programStudi = "Sistem Informasi"
    private val universitas  = "Politeknik Caltex Riau"
    private val email        = "febby24si@mahasiswa.ac.id"
    private val inisial      = "FBY"
    // ══════════════════════════════════════════════

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup Toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Profile"
            setDisplayShowTitleEnabled(true)
        }

        // Isi data profil
        binding.tvAvatarInitial.text = inisial
        binding.tvName.text          = namaLengkap
        binding.tvNim.text           = nim
        binding.tvProdi.text         = programStudi
        binding.tvUniversitas.text   = universitas
        binding.tvEmail.text         = email
        binding.tvRole.text          = "Mobile Developer · SiPedes"

        // Set placeholder TextInputLayout
        binding.etNamaPanggilan.setText(namaLengkap.split(" ").firstOrNull() ?: "")
        binding.etStatusBio.setText("Mobile Developer · SiPedes")

        // ✅ MaterialButton — Simpan
        binding.btnSimpanProfil.setOnClickListener {
            val namaPanggilan = binding.etNamaPanggilan.text.toString().trim()
            val statusBio     = binding.etStatusBio.text.toString().trim()

            // Validasi TextInputLayout
            var valid = true

            if (namaPanggilan.isEmpty()) {
                binding.tilNamaPanggilan.error = "Nama panggilan tidak boleh kosong"
                valid = false
            } else {
                binding.tilNamaPanggilan.error = null
            }

            if (statusBio.isEmpty()) {
                binding.tilStatusBio.error = "Status tidak boleh kosong"
                valid = false
            } else if (statusBio.length > 60) {
                binding.tilStatusBio.error = "Maksimal 60 karakter"
                valid = false
            } else {
                binding.tilStatusBio.error = null
            }

            if (valid) {
                // Update tampilan nama dan role di header
                binding.tvName.text = namaLengkap
                binding.tvRole.text = statusBio

                // Update inisial avatar berdasarkan nama panggilan
                val newInisial = namaPanggilan.take(3).uppercase()
                binding.tvAvatarInitial.text = newInisial

                Snackbar.make(
                    binding.root,
                    "✅ Profil berhasil diperbarui!",
                    Snackbar.LENGTH_LONG
                ).setBackgroundTint(requireContext().getColor(R.color.primaryGreen))
                    .setTextColor(requireContext().getColor(R.color.white))
                    .setAction("OK") {}
                    .show()
            }
        }

        // ✅ MaterialButton Outlined — Reset
        binding.btnResetProfil.setOnClickListener {
            binding.etNamaPanggilan.setText(namaLengkap.split(" ").firstOrNull() ?: "")
            binding.etStatusBio.setText("Mobile Developer · SiPedes")
            binding.tilNamaPanggilan.error = null
            binding.tilStatusBio.error    = null

            Snackbar.make(
                binding.root,
                "Formulir direset ke data awal",
                Snackbar.LENGTH_SHORT
            ).setBackgroundTint(requireContext().getColor(R.color.mediumText))
                .setTextColor(requireContext().getColor(R.color.white))
                .show()
        }

        // Animasi fade in
        val fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        binding.root.startAnimation(fadeIn)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}