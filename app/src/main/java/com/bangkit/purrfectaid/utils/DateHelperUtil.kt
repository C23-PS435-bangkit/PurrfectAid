package com.bangkit.purrfectaid.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * Created by Yosua on 16/06/2023
 */

fun parseDateRaw(timestamp: String): String {
    return try {
        val formatDate = SimpleDateFormat(timestampFormat, Locale.getDefault())
        formatDate.timeZone = TimeZone.getTimeZone("UTC")
        val value = formatDate.parse(timestamp)
        val dateFormatter =
            SimpleDateFormat("MMM-dd-yyyy hh:mm a", Locale.getDefault())
        dateFormatter.timeZone = TimeZone.getDefault()
        dateFormatter.format(value!!)
    } catch (e: ParseException) {
        getCurrentDate()
    }
}

private const val timestampFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

private fun getCurrentDate(): String {
    return Date().toString()
}