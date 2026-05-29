package com.example.rezy_esther.pertemuan_10

/**
 * UmkmModel — merepresentasikan satu produk/usaha UMKM warga desa
 * @param namaUsaha  nama produk atau nama usaha
 * @param pemilik    nama pemilik usaha
 * @param kategori   kategori usaha (Kuliner, Kerajinan, Pertanian, dst.)
 * @param harga      harga produk / estimasi omzet
 * @param imageUrl   URL gambar produk (dari picsum.photos)
 */
data class UmkmModel(
    val namaUsaha: String,
    val pemilik: String,
    val kategori: String,
    val harga: String,
    val imageUrl: String
)