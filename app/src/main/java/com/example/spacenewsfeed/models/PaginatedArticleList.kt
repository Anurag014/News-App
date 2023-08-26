package com.example.spacenewsfeed.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginatedArticleList(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: MutableList<Article>
)
