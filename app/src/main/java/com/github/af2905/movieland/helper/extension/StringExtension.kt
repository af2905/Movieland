package com.github.af2905.movieland.helper.extension

inline val String.Companion.empty get() = ""

fun String?.getFullPathToImage(): String? {
    if (this == null) return null
    return "https://image.tmdb.org/t/p/w500/$this"
}

fun String.getYearFromReleaseDate(): String = substringBefore("-")