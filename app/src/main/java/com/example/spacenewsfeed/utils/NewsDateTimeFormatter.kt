package com.example.spacenewsfeed.utils

import java.text.SimpleDateFormat
import java.util.*

fun newsFormatDateTime(inputDateTime: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("hh:mm a dd MMM yyyy", Locale.getDefault())

    val date = inputFormat.parse(inputDateTime)
    return outputFormat.format(date)
}