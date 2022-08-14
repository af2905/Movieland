package com.github.af2905.movieland.search.domain.params

data class PopularMoviesParams(
    val language: String? = null,
    val page: Int? = null,
    val region: String? = null,
    val forceUpdate: Boolean = false
)