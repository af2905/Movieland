package com.github.af2905.movieland.presentation.feature.home.popular

import com.github.af2905.movieland.presentation.base.UiEffect
import com.github.af2905.movieland.presentation.base.UiState
import com.github.af2905.movieland.presentation.common.effect.Navigate
import com.github.af2905.movieland.presentation.common.effect.ToastMessage
import com.github.af2905.movieland.presentation.model.Model

class PopularMovieContract {

    sealed class State : UiState() {

        data class Content(
            val isLoading: Boolean = false,
            val list: List<Model> = emptyList(),
            val error: Throwable? = null
        ) : PopularMovieContract.State()
    }

    sealed class Effect : UiEffect() {

        data class ShowFailMessage(val message: ToastMessage) : Effect()
        data class OpenMovieDetail(val navigator: Navigate) : Effect()
    }
}