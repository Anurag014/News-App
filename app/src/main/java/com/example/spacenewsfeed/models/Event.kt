package com.example.spacenewsfeed.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    @SerialName(value = "event_id")
    val eventId: Int,
    val provider: String
)
