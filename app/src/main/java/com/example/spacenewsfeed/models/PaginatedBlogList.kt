package com.example.spacenewsfeed.models

import kotlinx.serialization.Serializable

@Serializable
data class PaginatedBlogList(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Blog>
)
