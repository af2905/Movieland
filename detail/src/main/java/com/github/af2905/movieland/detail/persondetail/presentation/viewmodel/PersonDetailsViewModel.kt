package com.github.af2905.movieland.detail.persondetail.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.common.network.ResultWrapper
import com.github.af2905.movieland.core.repository.PeopleRepository
import com.github.af2905.movieland.detail.persondetail.presentation.state.PersonDetailsAction
import com.github.af2905.movieland.detail.persondetail.presentation.state.PersonDetailsEffect
import com.github.af2905.movieland.detail.persondetail.presentation.state.PersonDetailsState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = PersonDetailsViewModel.Factory::class)
class PersonDetailsViewModel @AssistedInject constructor(
    @Assisted private val personId: Int,
    private val peopleRepository: PeopleRepository
) : ViewModel() {

    var state by mutableStateOf(PersonDetailsState())
        private set

    private val _effect = Channel<PersonDetailsEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        fetchPersonDetails()
    }

    private fun fetchPersonDetails() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, isError = false)


            // Fetch person details
            val personResult = peopleRepository.getPersonDetails(personId, null)

            state = state.copy(
                person = (personResult as? ResultWrapper.Success)?.data,
                isError = personResult is ResultWrapper.Error
            )

            val externalIds =
                (peopleRepository.getPersonExternalIds(personId) as? ResultWrapper.Success)?.data

            externalIds?.let { socialIds ->
                state = state.copy(
                    personSocialIds = state.personSocialIds.copy(
                        wikidataId = socialIds.wikidataId,
                        facebookId = socialIds.facebookId,
                        instagramId = socialIds.instagramId,
                        twitterId = socialIds.twitterId,
                        tiktokId = socialIds.tiktokId,
                        youtubeId = socialIds.youtubeId
                    )
                )
            }

            launch {
                peopleRepository.getPersonCredits(personId, null).collectLatest { result ->
                    state = state.copy(
                        credits = (result as? ResultWrapper.Success)?.data ?: emptyList()
                    )
                }
            }

            state = state.copy(isLoading = false)
        }
    }

    fun onAction(action: PersonDetailsAction) {
        when (action) {
            is PersonDetailsAction.BackClick -> {
                viewModelScope.launch { _effect.send(PersonDetailsEffect.NavigateBack) }
            }

            is PersonDetailsAction.OpenCredit -> {
                viewModelScope.launch {
                    _effect.send(
                        PersonDetailsEffect.NavigateToCredit(
                            creditId = action.creditId,
                            type = action.type
                        )
                    )
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(personId: Int): PersonDetailsViewModel
    }
}
