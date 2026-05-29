package com.example.rezy_esther.pertemuan_10

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rezy_esther.databinding.ItemUmkmBinding

class UmkmAdapter(
    private val umkmList: List<UmkmModel>,
    private val onItemClick: (UmkmModel) -> Unit
) : RecyclerView.Adapter<UmkmAdapter.UmkmViewHolder>() {

    inner class UmkmViewHolder(val binding: ItemUmkmBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UmkmViewHolder {
        val binding = ItemUmkmBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return UmkmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UmkmViewHolder, position: Int) {
        val item = umkmList[position]
        with(holder.binding) {
            tvNamaUsaha.text = item.namaUsaha
            tvPemilik.text   = item.pemilik
            tvKategori.text  = item.kategori
            tvHarga.text     = item.harga
            Glide.with(holder.itemView.context)
                .load(item.imageUrl)
                .centerCrop()
                .into(imgUmkm)
            root.setOnClickListener { onItemClick(item) }
        }
    }

    override fun getItemCount(): Int = umkmList.size
}