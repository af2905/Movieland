package com.github.af2905.movieland.data.entity

data class MoviesResponseEntity(
    val dates: DatesEntity?,
    val page: Int,
    val movies: List<MovieEntity>,
    val totalPages: Int,
    val totalResults: Int
)

data class DatesEntity(val maximum: String, val minimum: String)

data class MovieEntity(
    val id: Int,
    val adult: Boolean,
    val backdropPath: String?,
    val genreIds: List<Int> = listOf(),
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)