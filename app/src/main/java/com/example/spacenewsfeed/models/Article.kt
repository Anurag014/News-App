package com.example.spacenewsfeed.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id: Int,
    val title: String,
    val url: String,
    @SerialName(value = "image_url")
    val imageUrl: String,
    @SerialName(value = "news_site")
    val newsSite: String,
    val summary: String,
    @SerialName(value = "published_at")
    val publishedAt: String,
    @SerialName(value = "updated_at")
    val updatedAt: String,
    val featured: Boolean,
    val launches: List<Launch>,
    val events: List<Event>
)