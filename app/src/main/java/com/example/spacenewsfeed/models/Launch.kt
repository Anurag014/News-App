package com.example.spacenewsfeed.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Launch(
    @SerialName(value = "launch_id")
    val launchId: String,
    val provider: String
)
