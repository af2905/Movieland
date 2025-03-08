package com.github.af2905.movieland.detail.persondetail.presentation.state

import com.github.af2905.movieland.core.data.database.entity.MediaType
import com.github.af2905.movieland.core.data.database.entity.PersonCreditsCast
import com.github.af2905.movieland.core.data.database.entity.PersonDetail

data class PersonDetailsState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val person: PersonDetail? = null,
    val credits: List<PersonCreditsCast> = emptyList(),
    val personSocialIds: PersonSocialIds = PersonSocialIds()
) {
    data class PersonSocialIds(
        val wikidataId: String? = null,
        val facebookId: String? = null,
        val instagramId: String? = null,
        val twitterId: String? = null,
        val tiktokId: String? = null,
        val youtubeId: String? = null
    )
}

sealed interface PersonDetailsAction {
    data object BackClick : PersonDetailsAction
    data class OpenCredit(val creditId: Int, val type: MediaType) : PersonDetailsAction
}

sealed interface PersonDetailsEffect {
    data object NavigateBack : PersonDetailsEffect
    data class NavigateToCredit(val creditId: Int, val type: MediaType) : PersonDetailsEffect
}