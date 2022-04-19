package com.github.af2905.movieland.domain.usecase.params

data class PersonDetailParams(
    val movieId: Int,
    val language: String? = null
)