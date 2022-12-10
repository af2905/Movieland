package com.github.af2905.movieland.detail.usecase.params

data class SimilarMoviesParams(
    val movieId: Int,
    val language: String? = null,
    val page: Int? = null
)

data class MovieDetailsParams(
    val movieId: Int,
    val language: String? = null
)

data class MovieActorsParams(
    val movieId: Int,
    val language: String? = null
)