package com.github.af2905.movieland.core.common.model.item

data class MoviesResponse(
    val page: Int,
    val totalPages: Int,
    val totalResults: Int,
    val dates: Dates? = null,
    val movies: List<MovieItem>? = listOf()
)

data class Dates(val maximum: String, val minimum: String)


