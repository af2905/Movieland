package com.github.af2905.movieland.home.domain.params

data class PeopleParams(
    val language: String? = null,
    val page: Int? = null,
    val forceUpdate: Boolean = false
)

object CachedPeopleParams