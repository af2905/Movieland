package com.github.af2905.movieland.people.presentation

import com.github.af2905.movieland.core.base.UiEffect
import com.github.af2905.movieland.core.base.UiState
import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.model.Model
import com.github.af2905.movieland.core.common.model.item.ListPlaceholderItem

class PeopleContract {
    sealed class State(open val list: List<Model>) : UiState() {

        object Init : State(list = emptyList()) {
            override fun toString(): String = javaClass.simpleName
        }

        data class Loading(
            override val list: List<Model> = listOf(ListPlaceholderItem())
        ) : State(list)

        data class Content(override val list: List<Model>) : State(list)
        data class Error(override val list: List<Model>, val e: Throwable?) : State(list)
    }

    sealed class Effect : UiEffect() {
        data class OpenPersonDetail(val navigator: Navigate) : Effect()
    }
}

