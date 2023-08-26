package com.example.spacenewsfeed.repository

import com.example.spacenewsfeed.models.NewsArticleList
import com.example.spacenewsfeed.network.NewsApiService
import retrofit2.http.Path

interface NewsRepository {

    suspend fun getSportsNews(): NewsArticleList

    suspend fun getMoviesNews(): NewsArticleList

    suspend fun getGeneralNews(): NewsArticleList

    suspend fun searchNews(text: String): NewsArticleList

    suspend fun getScienceNews(): NewsArticleList
}

class NetworkNewsArticleListRepository(
    private val newsApiService: NewsApiService
): NewsRepository {

    override suspend fun getSportsNews(): NewsArticleList {
        return newsApiService.getSportsNews()
    }

    override suspend fun getGeneralNews(): NewsArticleList {
        return newsApiService.getGeneralNews()
    }

    override suspend fun getMoviesNews(): NewsArticleList {
        return newsApiService.getMoviesNews()
    }

    override suspend fun searchNews(text: String): NewsArticleList {
        return newsApiService.searchNews(text)
    }

    override suspend fun getScienceNews(): NewsArticleList {
        return newsApiService.getScienceNews()
    }
}