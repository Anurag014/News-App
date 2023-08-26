package com.example.spacenewsfeed.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun formatDateTime(inputDateTime: String): String {
    val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'000'X")
    val outputFormat = DateTimeFormatter.ofPattern("hh:mm a dd MMM yyyy", Locale.getDefault())

    val instant = Instant.from(inputFormat.parse(inputDateTime))
    val zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
    return outputFormat.format(zonedDateTime)
}