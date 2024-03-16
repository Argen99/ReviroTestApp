package com.example.revirotestapp.core.extensions

import java.text.SimpleDateFormat
import java.util.Locale

fun String.toImageUrl(size: Int = 2) = "http://openweathermap.org/img/wn/${this}@${size}x.png"

fun String.toFormattedTime(inputFormat: String, outputFormat: String): String? {
    val date = SimpleDateFormat(inputFormat, Locale.getDefault()).parse(this)
    return date?.let { SimpleDateFormat(outputFormat, Locale.getDefault()).format(date) }
}
