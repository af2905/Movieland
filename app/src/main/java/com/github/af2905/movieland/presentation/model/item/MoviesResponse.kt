package com.github.af2905.movieland.presentation.model.item

data class MoviesResponse(
    val page: Int,
    val totalPages: Int,
    val totalResults: Int,
    val movieType: String = "",
    val dates: Dates? = null,
    val movies: List<MovieItem>? = listOf()
)

data class Dates(val maximum: String, val minimum: String)


