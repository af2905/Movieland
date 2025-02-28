package com.github.af2905.movieland.core.data.database.entity

enum class MediaType(val type: String) {
    MOVIE("movie"),
    TV("tv"),
    PERSON("person");

    companion object {
        fun fromString(value: String?): MediaType? {
            return entries.find { it.type.equals(value, ignoreCase = true) }
        }
    }
}