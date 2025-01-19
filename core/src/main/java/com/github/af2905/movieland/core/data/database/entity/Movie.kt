package com.github.af2905.movieland.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey val id: Int,
    val adult: Boolean?,
    val genreIds: List<Int>?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val backdropPath: String?,
    val posterPath: String?,
    val movieType: MovieType? = null,
    val timeStamp: Long? = null
)

enum class MovieType {
    POPULAR,
    NOW_PLAYING,
    TOP_RATED,
    UPCOMING,
    TRENDING_DAY,
    TRENDING_WEEK
}