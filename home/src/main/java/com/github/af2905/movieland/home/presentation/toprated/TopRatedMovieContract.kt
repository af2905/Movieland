package com.github.af2905.movieland.home.presentation.toprated

import com.github.af2905.movieland.core.base.UiEffect
import com.github.af2905.movieland.core.base.UiState
import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.effect.ToastMessage
import com.github.af2905.movieland.core.common.model.Model
import com.github.af2905.movieland.home.presentation.item.ListMovieVariantPlaceholderItem

class TopRatedMovieContract {

    sealed class State(open val list: List<Model>) : UiState() {

        data class Init(
            override val list: List<Model> = listOf(ListMovieVariantPlaceholderItem())
        ) : State(list)

        data class Content(override val list: List<Model>) : State(list)
        data class Error(override val list: List<Model>, val e: Throwable?) : State(list)
    }

    sealed class Effect : UiEffect() {

        data class ShowFailMessage(val message: ToastMessage) : Effect()
        data class OpenMovieDetail(val navigator: Navigate) : Effect()
    }
}