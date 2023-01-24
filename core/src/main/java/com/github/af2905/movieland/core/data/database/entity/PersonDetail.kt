package com.github.af2905.movieland.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PersonDetail(
    @PrimaryKey val id: Int,
    val name: String?,
    val birthday: String?,
    val knownForDepartment: String?,
    val deathDay: String?,
    val gender: Int?,
    val popularity: Double?,
    val biography: String?,
    val placeOfBirth: String?,
    val profilePath: String?,
    val adult: Boolean?,
    val homepage: String?,
    val liked: Boolean,
    val personMovieCreditsCasts: List<PersonMovieCreditsCast> = emptyList()
)

data class PersonMovieCreditsCast(
    val id: Int,
    val adult: Boolean?,
    val backdropPath: String?,
    val genreIds: List<Int>?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val character: String?,
    val creditId: String?,
    val order: Int?
)