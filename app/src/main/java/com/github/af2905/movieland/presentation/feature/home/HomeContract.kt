package com.github.af2905.movieland.presentation.feature.home

import com.github.af2905.movieland.presentation.base.UiEffect
import com.github.af2905.movieland.presentation.base.UiState
import com.github.af2905.movieland.presentation.common.effect.Navigate
import com.github.af2905.movieland.presentation.common.effect.ToastMessage
import com.github.af2905.movieland.presentation.feature.home.item.HomeMenuPlaceholderItem
import com.github.af2905.movieland.presentation.model.Model

class HomeContract {

    sealed class State(open val list: List<Model>) : UiState() {

        data class Init(
            override val list: List<Model> = listOf(HomeMenuPlaceholderItem())
        ) : State(list)

        data class Loading(override val list: List<Model>) : State(list)
        data class Content(override val list: List<Model>) : State(list)
        data class Error(override val list: List<Model>, val e: Throwable?) : State(list)
    }

    sealed class Effect : UiEffect() {

        data class ShowFailMessage(val message: ToastMessage) : Effect()
        data class OpenMovieDetail(val navigator: Navigate) : Effect()
    }
}