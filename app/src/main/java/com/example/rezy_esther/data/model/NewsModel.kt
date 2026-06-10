package com.example.rezy_esther.data.model

import com.google.gson.annotations.SerializedName

/**
 * Root response dari GNews API
 * GET https://gnews.io/api/v4/top-headlines?country=id&lang=id
 */
data class NewsResponse(
    @SerializedName("totalArticles")
    val totalArticles: Int,

    @SerializedName("articles")
    val articles: List<NewsArticle>
)

/**
 * Satu item artikel berita
 */
data class NewsArticle(
    @SerializedName("title")
    val title: String,                  // judul berita

    @SerializedName("description")
    val description: String?,           // ringkasan — nullable

    @SerializedName("content")
    val content: String?,               // isi konten — nullable

    @SerializedName("url")
    val url: String,                    // URL artikel lengkap

    @SerializedName("image")
    val urlToImage: String?,            // URL gambar thumbnail — nullable

    @SerializedName("publishedAt")
    val publishedAt: String,            // ISO 8601: "2025-06-07T10:30:00Z"

    @SerializedName("source")
    val source: NewsSource              // nama sumber
)

/**
 * Sumber berita
 */
data class NewsSource(
    @SerializedName("name")
    val name: String,                   // nama tampil: "Kompas", "Detik"

    @SerializedName("url")
    val url: String                     // URL sumber
)