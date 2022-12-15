package com.github.af2905.movieland.core.data

private const val EXCEPTION_MESSAGE = "unsupported MediaType"

enum class MediaType(val type: String) {
    MOVIE("movie"),
    PERSON("person"),
    TV("tv")
}

fun String.toMediaType() = when (this) {
    MediaType.MOVIE.type -> MediaType.MOVIE
    MediaType.PERSON.type -> MediaType.PERSON
    MediaType.TV.type -> MediaType.TV
    else -> throw IllegalStateException(EXCEPTION_MESSAGE)
}