package com.example.eventuree.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

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