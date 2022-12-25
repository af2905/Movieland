package com.github.af2905.movieland.home.domain.params

import com.github.af2905.movieland.core.data.database.entity.MovieType

data class NowPlayingMoviesParams(
    override val language: String? = null,
    override val page: Int? = null,
    override val region: String? = null,
    override val forceUpdate: Boolean = false
) : BaseMoviesParams

data class PopularMoviesParams(
    override val language: String? = null,
    override val page: Int? = null,
    override val region: String? = null,
    override val forceUpdate: Boolean = false
) : BaseMoviesParams

data class TopRatedMoviesParams(
    override val language: String? = null,
    override val page: Int? = null,
    override val region: String? = null,
    override val forceUpdate: Boolean = false
) : BaseMoviesParams

data class UpcomingMoviesParams(
    override val language: String? = null,
    override val page: Int? = null,
    override val region: String? = null,
    override val forceUpdate: Boolean = false
) : BaseMoviesParams

data class RecommendedMoviesParams(
    val movieId: Int,
    val language: String? = null,
    val page: Int? = null
)

data class CachedMoviesParams(val type: MovieType)

interface BaseMoviesParams {
    val language: String?
    val page: Int?
    val region: String?
    val forceUpdate: Boolean
}
