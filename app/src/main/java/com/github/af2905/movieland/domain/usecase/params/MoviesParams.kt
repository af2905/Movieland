package com.github.af2905.movieland.domain.usecase.params

import com.github.af2905.movieland.helper.LanguageHelper

data class NowPlayingMoviesParams(
    override val language: String? = LanguageHelper.getRussianLanguageOrDefault(),
    override val page: Int? = null,
    override val region: String? = null,
    override val forceUpdate: Boolean = false
) : BaseMoviesParams

data class PopularMoviesParams(
    override val language: String? = LanguageHelper.getRussianLanguageOrDefault(),
    override val page: Int? = null,
    override val region: String? = null,
    override val forceUpdate: Boolean = false
) : BaseMoviesParams

data class TopRatedMoviesParams(
    override val language: String? = LanguageHelper.getRussianLanguageOrDefault(),
    override val page: Int? = null,
    override val region: String? = null,
    override val forceUpdate: Boolean = false
) : BaseMoviesParams

data class UpcomingMoviesParams(
    override val language: String? = LanguageHelper.getRussianLanguageOrDefault(),
    override val page: Int? = null,
    override val region: String? = null,
    override val forceUpdate: Boolean = false
) : BaseMoviesParams

data class RecommendedMoviesParams(
    val movieId: Int,
    val language: String? = LanguageHelper.getRussianLanguageOrDefault(),
    val page: Int? = null
)

data class SimilarMoviesParams(
    val movieId: Int,
    val language: String? = LanguageHelper.getRussianLanguageOrDefault(),
    val page: Int? = null
)

data class MovieDetailsParams(
    val movieId: Int,
    val language: String? = LanguageHelper.getRussianLanguageOrDefault()
)

data class MovieActorsParams(
    val movieId: Int,
    val language: String? = LanguageHelper.getRussianLanguageOrDefault()
)

interface BaseMoviesParams {
    val language: String?
    val page: Int?
    val region: String?
    val forceUpdate: Boolean
}
