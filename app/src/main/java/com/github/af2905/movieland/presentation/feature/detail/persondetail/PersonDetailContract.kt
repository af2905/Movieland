package com.github.af2905.movieland.presentation.feature.detail.persondetail

import com.github.af2905.movieland.presentation.base.UiEffect
import com.github.af2905.movieland.presentation.base.UiState
import com.github.af2905.movieland.presentation.common.effect.ToastMessage
import com.github.af2905.movieland.presentation.model.item.PersonItem

class PersonDetailContract {

    sealed class State : UiState() {

        object Loading : State() {
            override fun toString(): String = javaClass.simpleName
        }

        data class Content(val personItem: PersonItem) : State()
        data class Error(val e: Throwable?) : State()
    }

    sealed class Effect : UiEffect() {

        data class ShowFailMessage(val message: ToastMessage) : Effect()
    }
}