package com.github.af2905.movieland.domain.usecase.params

data class PersonParams(
    val movieId: Int,
    val language: String? = null
)