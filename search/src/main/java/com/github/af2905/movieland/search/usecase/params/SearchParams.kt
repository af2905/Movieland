package com.github.af2905.movieland.search.usecase.params

data class SearchMovieParams(
    val query: String,
    val language: String? = null,
    val page: Int? = null,
    val adult: String? = null,
    val region: String? = null,
    val year: Int? = null
)