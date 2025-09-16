package com.example.eventuree.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun getDay(dateTimeString: String): String {
    val zonedDateTime = ZonedDateTime.parse(dateTimeString)
    return zonedDateTime.format(DateTimeFormatter.ofPattern("dd")) // "15"
}

@RequiresApi(Build.VERSION_CODES.O)
fun getMonth(dateTimeString: String): String {
    val zonedDateTime = ZonedDateTime.parse(dateTimeString)
    return zonedDateTime.format(DateTimeFormatter.ofPattern("MMM")) // "Nov"
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatEventDate(isoDate: String): String {
    return try {
        val zonedDateTime = ZonedDateTime.parse(isoDate) // parses "2025-06-15T10:00:00.000Z"
        val formatter = DateTimeFormatter.ofPattern("EEE, MMM d · h:mm a", Locale.getDefault())
        zonedDateTime.format(formatter)
    } catch (e: Exception) {
        isoDate // fallback in case parsing fails
    }
}