package com.example.rezy_esther

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.rezy_esther.R
import com.example.rezy_esther.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    // ══════════════════════════════════════════════════
    // 🔧 GANTI DATA INI DENGAN DATA KAMU SENDIRI!
    // ══════════════════════════════════════════════════
    private val namaLengkap   = "Febby Fahrezy"
    private val nim            = "2457301054"
    private val programStudi   = "Sistem Informasi"
    private val universitas    = "Politeknik Caltex Riau"
    private val email          = "febby24si@mahasiswa.ac.id"
    private val inisial        = "FBY"   // 2 huruf pertama nama
    // ══════════════════════════════════════════════════

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
        binding.tvAvatarInitial.text  = inisial
        binding.tvName.text           = namaLengkap
        binding.tvNim.text            = nim
        binding.tvProdi.text          = programStudi
        binding.tvUniversitas.text    = universitas
        binding.tvEmail.text          = email

        // Role subtitle (opsional override)
        binding.tvRole.text = "Mobile Developer · SiPedes"

        // Animasi fade in
        val fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        val slideUp = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)
        binding.root.startAnimation(fadeIn)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
