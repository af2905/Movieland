package com.github.af2905.movieland.detail.persondetail.presentation

import com.github.af2905.movieland.core.base.UiEffect
import com.github.af2905.movieland.core.base.UiState
import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.effect.ToastMessage
import com.github.af2905.movieland.core.common.model.Model
import com.github.af2905.movieland.core.common.model.item.BackButtonItem
import com.github.af2905.movieland.core.common.model.item.ErrorItem
import com.github.af2905.movieland.core.common.model.item.PersonDetailItem

class PersonDetailContract {

    sealed class State : UiState() {

        object Loading : State() {
            override fun toString(): String = javaClass.simpleName
        }

        data class Content(
            val personDetailItem: PersonDetailItem,
            val list: List<Model> = emptyList()
        ) : State()

        data class Error(
            val errorItem: ErrorItem = ErrorItem(),
            val backButtonItem: BackButtonItem = BackButtonItem(),
            val e: Throwable?
        ) : State()

        fun toContent(): Content? = if (this is Content) this else null
        fun toError(): Error? = if (this is Error) this else null
    }

    sealed class Effect : UiEffect() {

        data class ShowFailMessage(val message: ToastMessage) : Effect()
        data class OpenMovieDetail(val navigator: Navigate) : Effect()
        data class OpenPreviousScreen(val navigator: Navigate) : Effect()
    }
}