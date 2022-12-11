package com.github.af2905.movieland.detail.usecase.params

import com.github.af2905.movieland.core.common.model.item.MovieDetailItem

data class SimilarMoviesParams(
    val movieId: Int,
    val language: String? = null,
    val page: Int? = null
)

data class MovieDetailParams(val movieId: Int, val language: String? = null)
data class MovieActorsParams(val movieId: Int, val language: String? = null)

data class LikedMovieDetailParams(val movieDetail: MovieDetailItem)
data class UnlikedMovieDetailParams(val movieDetail: MovieDetailItem)
data class GetLikedMovieDetailByIdParams(val movieId: Int)