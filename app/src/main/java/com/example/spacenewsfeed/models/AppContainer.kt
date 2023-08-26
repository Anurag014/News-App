package com.example.spacenewsfeed.models

import com.example.spacenewsfeed.network.ApiService
import com.example.spacenewsfeed.network.NewsApiService
import com.example.spacenewsfeed.repository.NetworkNewsArticleListRepository
import com.example.spacenewsfeed.repository.NetworkPaginatedArticleListRepository
import com.example.spacenewsfeed.repository.NewsRepository
import com.example.spacenewsfeed.repository.SpaceNewsRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val spaceNewsRepository: SpaceNewsRepository
    val newsRepository: NewsRepository
}

class DefaultAppContainer: AppContainer {

    private val baseUrl = "https://api.spaceflightnewsapi.net/v4/"
    private val baseUrl1 = "https://newsapi.org/v2/"

    val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    val retrofit1 = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl1)
        .build()

    private val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    private val retrofitNewsService: NewsApiService by lazy {
        retrofit1.create(NewsApiService::class.java)
    }

    override val spaceNewsRepository: SpaceNewsRepository by lazy {
        NetworkPaginatedArticleListRepository(retrofitService)
    }

    override val newsRepository: NewsRepository by lazy {
        NetworkNewsArticleListRepository(retrofitNewsService)
    }

}