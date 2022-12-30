package com.github.af2905.movieland.home.domain.params

import com.github.af2905.movieland.core.data.database.entity.TvShowType

data class TvShowsParams(
    val language: String? = null,
    val page: Int? = null,
    val forceUpdate: Boolean = false
)

data class CachedTvShowsParams(val type: TvShowType)