package com.example.spacenewsfeed.network

import com.example.spacenewsfeed.models.NewsArticleList
import org.intellij.lang.annotations.Language
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines?category=sports&country=in&pageSize=50&apiKey=e0f0dfb514b440d4b938df5ba515cb4a")
    suspend fun getSportsNews(): NewsArticleList

    @GET("top-headlines?category=entertainment&country=in&pageSize=50&apiKey=e0f0dfb514b440d4b938df5ba515cb4a")
    suspend fun getMoviesNews(): NewsArticleList

    @GET("top-headlines?category=general&country=in&pageSize=50&apiKey=e0f0dfb514b440d4b938df5ba515cb4a")
    suspend fun getGeneralNews(): NewsArticleList

    @GET("top-headlines?category=science&country=in&pageSize=50&apiKey=e0f0dfb514b440d4b938df5ba515cb4a")
    suspend fun getScienceNews(): NewsArticleList

    @GET("everything")
    suspend fun searchNews(
        @Query("q") text: String,
        @Query("language") language: String = "en",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String = "e0f0dfb514b440d4b938df5ba515cb4a"
    ): NewsArticleList
}



// API key = e0f0dfb514b440d4b938df5ba515cb4a