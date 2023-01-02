package com.github.af2905.movieland.search.domain.params

data class SearchMovieParams(
    val query: String,
    val language: String? = null,
    val page: Int? = null,
    val adult: String? = null,
    val region: String? = null,
    val year: Int? = null
)

data class SearchMultiParams(
    val query: String,
    val language: String? = null,
    val page: Int? = null,
    val adult: String? = null,
    val region: String? = null
)

object SearchParams