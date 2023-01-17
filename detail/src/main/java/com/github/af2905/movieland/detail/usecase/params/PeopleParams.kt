package com.github.af2905.movieland.detail.usecase.params

import com.github.af2905.movieland.core.common.model.item.PersonDetailItem

data class PersonDetailParams(
    val personId: Int,
    val language: String? = null
)

data class PersonMovieCreditsParams(
    val personId: Int,
    val language: String? = null
)

data class LikedPersonDetailParams(val personDetail: PersonDetailItem)
data class UnlikedPersonDetailParams(val personDetail: PersonDetailItem)
data class GetLikedPersonDetailByIdParams(val personId: Int)