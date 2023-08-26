package com.example.spacenewsfeed.models

import kotlinx.serialization.Serializable

@Serializable
data class NewsArticleList(
    val status: String,
    val totalResults: Int,
    val articles: MutableList<NewsArticle>
)
