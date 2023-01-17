package com.github.af2905.movieland.util.extension

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

inline val String.Companion.empty get() = ""

fun String?.getFullPathToImage(): String? {
    if (this == null) return null
    return "https://image.tmdb.org/t/p/original$this"
}

fun String.getYearFromReleaseDate(): String {
    val pattern = "yyyy"
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault()))
                .format(DateTimeFormatter.ofPattern(pattern, Locale.getDefault()))
        } else {
            val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
            simpleDateFormat.format(this)
        }
    } catch (e: Exception) {
        substringBefore("-")
    }
}


fun String.formatDate(): String {
    val pattern = "dd MMMM yyyy"
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault()))
                .format(DateTimeFormatter.ofPattern(pattern, Locale.getDefault()))
        } else {
            val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
            simpleDateFormat.format(this)
        }
    } catch (e: Exception) {
        this
    }
}