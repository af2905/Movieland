package com.github.af2905.movieland.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Genre(
    @PrimaryKey val id: Int,
    val name: String,
    val genreType: GenreType,
    val timeStamp: Long? = null
)

enum class GenreType {
    MOVIE, TV_SHOW
}