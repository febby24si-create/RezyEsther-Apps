package com.example.rezy_esther.data.repository

import android.util.Log
import com.example.rezy_esther.data.api.NewsApiClient
import com.example.rezy_esther.data.model.NewsArticle

class NewsRepository {

    private val TOKEN = "46a554fd427be0c1c612f3e4ef4a7e9c"

    companion object {
        private const val TAG = "NewsRepository"
    }

    suspend fun getTopHeadlines(): Result<List<NewsArticle>> {
        return try {
            Log.d(TAG, "Memanggil GNews top-headlines...")

            val response = NewsApiClient.apiService.getTopHeadlines(
                country = "id",
                lang    = "id",
                max     = 20,
                token   = TOKEN
            )

            Log.d(TAG, "Response diterima. Total: ${response.totalArticles}")
            Log.d(TAG, "Jumlah artikel: ${response.articles.size}")

            val valid = response.articles.filter { article ->
                article.title.isNotBlank() &&
                        article.title != "[Removed]"
            }

            Log.d(TAG, "Artikel valid: ${valid.size}")

            if (valid.isEmpty()) fetchFallback()
            else Result.success(valid)
        } catch (e: Exception) {
            Log.e(TAG, "Error top-headlines: ${e.javaClass.simpleName} — ${e.message}")
            fetchFallback()
        }
    }

    private suspend fun fetchFallback(): Result<List<NewsArticle>> {
        return try {
            Log.d(TAG, "Mencoba fallback /search...")

            val response = NewsApiClient.apiService.searchNews(
                query   = "indonesia",
                lang    = "id",
                country = "id",
                max     = 20,
                token   = TOKEN
            )

            Log.d(TAG, "Fallback response. Total: ${response.totalArticles}")

            val valid = response.articles.filter { article ->
                article.title.isNotBlank() &&
                        article.title != "[Removed]"
            }

            if (valid.isEmpty()) Result.failure(Exception("Tidak ada berita tersedia"))
            else Result.success(valid)

        } catch (e: Exception) {
            Log.e(TAG, "Error fallback: ${e.javaClass.simpleName} — ${e.message}")
            Result.failure(Exception("${e.javaClass.simpleName}: ${e.message}"))
        }
    }
}