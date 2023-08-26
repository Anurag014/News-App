package com.example.spacenewsfeed.repository

import com.example.spacenewsfeed.models.PaginatedArticleList
import com.example.spacenewsfeed.models.PaginatedBlogList
import com.example.spacenewsfeed.models.PaginatedReportList
import com.example.spacenewsfeed.network.ApiService


interface SpaceNewsRepository {
    suspend fun getPaginatedArticleList(): PaginatedArticleList

    suspend fun getPaginatedReportList(): PaginatedReportList

    suspend fun getPaginatedBlogList(): PaginatedBlogList
}

class NetworkPaginatedArticleListRepository(
    private val apiService: ApiService
): SpaceNewsRepository {
    override suspend fun getPaginatedArticleList(): PaginatedArticleList {
        return apiService.getPaginatedArticleList()
    }

    override suspend fun getPaginatedReportList(): PaginatedReportList {
        return apiService.getPaginatedReportList()
    }

    override suspend fun getPaginatedBlogList(): PaginatedBlogList {
        return apiService.getPaginatedBlogList()
    }
}