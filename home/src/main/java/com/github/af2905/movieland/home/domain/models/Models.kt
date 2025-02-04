package com.github.af2905.movieland.home.domain.models

import com.github.af2905.movieland.core.data.database.entity.Genre

data class GenreItem(
    val id: Int,
    val icon: String,
    val title: String
)

internal fun List<Genre>.getMovieGenreItems(): List<GenreItem> {
    return GenreItemMapper.mapGenresToGenreItems(this)
}

internal fun List<Genre>.getTvShowGenreItems(): List<GenreItem> {
    return GenreItemMapper.mapGenresToGenreItems(this)
}

object GenreItemMapper {
    // Define emoji mapping for genre names or IDs
    private val genreEmojiMap = mapOf(
        "Action" to "🔥",
        "Adventure" to "🏕️",
        "Animation" to "🎨",
        "Comedy" to "😂",
        "Crime" to "🕵️‍♂️",
        "Documentary" to "🎥",
        "Drama" to "🎭",
        "Family" to "👨‍👩‍👧",
        "Fantasy" to "🧙‍♂️",
        "History" to "📜",
        "Horror" to "👻",
        "Music" to "🎵",
        "Mystery" to "🕵️",
        "Romance" to "❤️",
        "Science Fiction" to "🚀",
        "TV Movie" to "📺",
        "Thriller" to "🔪",
        "War" to "⚔️",
        "Western" to "🤠",
        "Action & Adventure" to "🏹",
        "Kids" to "🧒",
        "News" to "📰",
        "Reality" to "🎤",
        "Sci-Fi & Fantasy" to "🌌",
        "Soap" to "🧼",
        "Talk" to "🗣️",
        "War & Politics" to "🏛️"
    )

    fun mapGenresToGenreItems(genres: List<Genre>): List<GenreItem> {
        return genres.map { genre ->
            GenreItem(
                id = genre.id,
                icon = genreEmojiMap[genre.name] ?: "❓", // Default icon for unknown genres
                title = genre.name
            )
        }
    }
}
