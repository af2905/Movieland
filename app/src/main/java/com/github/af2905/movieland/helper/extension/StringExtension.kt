package com.github.af2905.movieland.helper.extension

fun String?.getFullPathToImage(): String? {
    if (this == null) return null
    return "https://image.tmdb.org/t/p/w500/$this"
}