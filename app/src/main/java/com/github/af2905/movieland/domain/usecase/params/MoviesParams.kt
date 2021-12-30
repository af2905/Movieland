package com.github.af2905.movieland.domain.usecase.params

data class NowPlayingMoviesParams(
    val language: String? = null,
    val page: Int? = null,
    val region: String? = null
)

data class PopularMoviesParams(
    val language: String? = null,
    val page: Int? = null,
    val region: String? = null
)

data class TopRatedMoviesParams(
    val language: String? = null,
    val page: Int? = null,
    val region: String? = null
)

data class UpcomingMoviesParams(
    val language: String? = null,
    val page: Int? = null,
    val region: String? = null
)

data class MovieDetailsParams(val movieId: Int, val language: String? = null)

data class RecommendedMoviesParams(
    val movieId: Int, val language: String? = null, val page: Int? = null
)

data class SimilarMoviesParams(
    val movieId: Int, val language: String? = null, val page: Int? = null
)

data class MovieActorsParams(val movieId: Int, val language: String? = null)


