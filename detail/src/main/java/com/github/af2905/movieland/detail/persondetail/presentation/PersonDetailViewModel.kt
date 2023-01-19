package com.github.af2905.movieland.detail.persondetail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.base.Container
import com.github.af2905.movieland.core.common.ErrorHandler
import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.effect.ToastMessage
import com.github.af2905.movieland.core.common.model.Model
import com.github.af2905.movieland.core.common.model.item.*
import com.github.af2905.movieland.detail.R
import com.github.af2905.movieland.detail.persondetail.PersonDetailNavigator
import com.github.af2905.movieland.detail.usecase.params.*
import com.github.af2905.movieland.detail.usecase.person.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import javax.inject.Inject

class PersonDetailViewModel @Inject constructor(
    private val personId: Int,
    private val getPersonDetail: GetPersonDetail,
    private val getPersonMovieCredits: GetPersonMovieCredits,
    private val saveToLikedPerson: SaveToLikedPerson,
    private val removeFromLikedPerson: RemoveFromLikedPerson,
    private val getLikedPersonById: GetLikedPersonById
) : ViewModel() {

    val container: Container<PersonDetailContract.State, PersonDetailContract.Effect> =
        Container(viewModelScope, PersonDetailContract.State.Loading)

    val personDetailClickListener = PersonDetailItem.Listener {
        container.intent {
            if (this.state.value is PersonDetailContract.State.Content) {
                val contentState = this.state.value as PersonDetailContract.State.Content
                handleLikePerson(contentState)
            }
        }
    }

    val errorItemClickListener = ErrorItem.Listener { loadData() }
    val backButtonItemClickListener = BackButtonItem.Listener { openPreviousScreen() }

    init {
        loadData()
    }

    private suspend fun handleLikePerson(state: PersonDetailContract.State.Content) {
        if (state.personDetailItem.liked) {
            removeFromLikedPerson(UnlikedPersonDetailParams(state.personDetailItem))
        } else {
            saveToLikedPerson(
                LikedPersonDetailParams(
                    state.personDetailItem.copy(
                        liked = !state.personDetailItem.liked
                    )
                )
            )
        }
        container.reduce {
            state.copy(
                personDetailItem = state.personDetailItem.copy(
                    liked = !state.personDetailItem.liked
                ),
                list = state.list
            )
        }
        container.postEffect(
            PersonDetailContract.Effect.LikeClicked
        )
    }

    private fun loadData() {
        container.intent {
            if (container.state.value !is PersonDetailContract.State.Loading) {
                container.reduce { PersonDetailContract.State.Loading }
            }
            try {
                handlePersonDetail(viewModelScope)
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private suspend fun handlePersonDetail(scope: CoroutineScope) {
        val list = mutableListOf<Model>()
        var personDetailItem =
            getLikedPersonById(GetLikedPersonDetailByIdParams(personId)).getOrThrow()

        if (personDetailItem == null) {
            val castsAsync = viewModelScope.async {
                getPersonMovieCredits(PersonMovieCreditsParams(personId)).getOrNull()
            }
            val personDetailAsync = scope.async {
                getPersonDetail(PersonDetailParams(personId)).getOrThrow()
            }

            val casts = castsAsync.await().orEmpty()
            personDetailItem = personDetailAsync.await()
            personDetailItem = personDetailItem.copy(personMovieCreditsCasts = casts)

            list.add(personDetailItem)
            list.addAll(createActorsAndCrewBlock(casts))
        } else {
            list.add(personDetailItem)
            list.addAll(createActorsAndCrewBlock(personDetailItem.personMovieCreditsCasts))
        }
        container.reduce {
            PersonDetailContract.State.Content(personDetailItem, list)
        }
    }

    private fun createActorsAndCrewBlock(personMovieCreditsCasts: List<PersonMovieCreditsCastItem>): List<Model> {
        return if (personMovieCreditsCasts.isNotEmpty()) {
            val list = mutableListOf<Model>()
            val knownForHeader = HeaderItem(res = R.string.person_detail_known_for_title)
            list.add(knownForHeader)
            personMovieCreditsCasts.forEach { list.add(it) }
            list
        } else {
            emptyList()
        }
    }

    private suspend fun handleError(e: Exception) {
        container.reduce {
            PersonDetailContract.State.Error(
                errorItem = ErrorItem(errorMessage = e.message.orEmpty()),
                e = e
            )
        }
        container.intent {
            container.postEffect(
                PersonDetailContract.Effect.ShowFailMessage(
                    ToastMessage(ErrorHandler.handleError(e))
                )
            )
        }
    }

    fun navigateToMovieDetail(itemId: Int) {
        container.intent {
            container.postEffect(PersonDetailContract.Effect.OpenMovieDetail(Navigate { navigator ->
                (navigator as PersonDetailNavigator).forwardMovieDetail(itemId)
            }))
        }
    }

    private fun openPreviousScreen() {
        container.intent {
            container.postEffect(PersonDetailContract.Effect.OpenPreviousScreen(Navigate { navigator ->
                navigator.back()
            }))
        }
    }
}