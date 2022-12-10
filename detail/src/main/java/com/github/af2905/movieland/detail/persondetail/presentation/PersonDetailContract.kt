package com.github.af2905.movieland.detail.persondetail.presentation

import com.github.af2905.movieland.core.base.UiEffect
import com.github.af2905.movieland.core.base.UiState
import com.github.af2905.movieland.core.common.effect.ToastMessage
import com.github.af2905.movieland.core.data.PersonDetailItem

class PersonDetailContract {

    sealed class State : UiState() {

        object Loading : State() {
            override fun toString(): String = javaClass.simpleName
        }

        data class Content(val personDetailItem: PersonDetailItem) : State()
        data class Error(val e: Throwable?) : State()
    }

    sealed class Effect : UiEffect() {

        data class ShowFailMessage(val message: ToastMessage) : Effect()
    }
}