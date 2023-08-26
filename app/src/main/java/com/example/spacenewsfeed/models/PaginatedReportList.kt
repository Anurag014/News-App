package com.example.spacenewsfeed.models

import kotlinx.serialization.Serializable

@Serializable
data class PaginatedReportList(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Report>
)
