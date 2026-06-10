package com.example.rezy_esther.data.api

import com.example.rezy_esther.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    /**
     * Top headlines Indonesia
     * https://gnews.io/api/v4/top-headlines?country=id&lang=id&token=...
     */
    @GET("v4/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country")  country: String  = "id",
        @Query("lang")     lang: String     = "id",
        @Query("max")      max: Int         = 20,
        @Query("token")    token: String
    ): NewsResponse

    /**
     * Search berita — fallback jika top-headlines kosong
     * https://gnews.io/api/v4/search?q=indonesia&lang=id&token=...
     */
    @GET("v4/search")
    suspend fun searchNews(
        @Query("q")        query: String,
        @Query("lang")     lang: String     = "id",
        @Query("country")  country: String  = "id",
        @Query("max")      max: Int         = 20,
        @Query("sortby")   sortBy: String   = "publishedAt",
        @Query("token")    token: String
    ): NewsResponse
}