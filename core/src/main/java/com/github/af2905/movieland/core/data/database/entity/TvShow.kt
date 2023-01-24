package com.github.af2905.movieland.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TvShow(
    @PrimaryKey val id: Int,
    val posterPath: String?,
    val popularity: Double?,
    val backdropPath: String?,
    val voteAverage: Double?,
    val overview: String?,
    val firstAirDate: String?,
    val originCountry: List<String>?,
    val genreIds: List<Int>?,
    val originalLanguage: String?,
    val voteCount: Int?,
    val name: String?,
    val originalName: String?,
    val tvShowType: String = "",
    val timeStamp: Long? = null
)

enum class TvShowType {
    POPULAR, TOP_RATED
}