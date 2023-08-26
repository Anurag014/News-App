package com.example.spacenewsfeed.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Info(
    val version: String,
    @SerialName(value = "news_sites")
    val newsSites: List<String>
)
