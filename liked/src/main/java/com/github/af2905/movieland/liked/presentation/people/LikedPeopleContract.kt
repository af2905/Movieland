package com.github.af2905.movieland.liked.presentation.people

import com.github.af2905.movieland.core.base.UiEffect
import com.github.af2905.movieland.core.base.UiState
import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.model.Model

class LikedPeopleContract {
    sealed class State : UiState() {

        object Loading : State() {
            override fun toString(): String = javaClass.simpleName
        }

        data class Content(val list: List<Model>) : State()

        fun toContent(): Content? = if (this is Content) this else null
    }

    sealed class Effect : UiEffect() {
        data class OpenPersonDetail(val navigator: Navigate) : Effect()
    }
}