package com.example.spacenewsfeed.network

import com.example.spacenewsfeed.models.PaginatedArticleList
import com.example.spacenewsfeed.models.PaginatedBlogList
import com.example.spacenewsfeed.models.PaginatedReportList
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("articles/?limit=50")
    suspend fun getPaginatedArticleList(): PaginatedArticleList

    @GET("reports/?limit=50")
    suspend fun getPaginatedReportList(): PaginatedReportList

    @GET("blogs/?limit=50")
    suspend fun getPaginatedBlogList(): PaginatedBlogList
}