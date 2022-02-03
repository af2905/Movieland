package com.github.af2905.movieland.presentation.feature.home

import com.github.af2905.movieland.presentation.base.UiEffect
import com.github.af2905.movieland.presentation.base.UiState
import com.github.af2905.movieland.presentation.common.effect.Navigate
import com.github.af2905.movieland.presentation.common.effect.ToastMessage
import com.github.af2905.movieland.presentation.model.Model

class HomeContract {

    sealed class State : UiState() {

        data class Loading(val movies: List<Model> = emptyList()) : State()
        data class Success(val movies: List<Model>) : State()

        data class Error(val e: Throwable?) : State()
        object EmptyResult : State()
    }

    sealed class Effect : UiEffect() {

        data class ShowFailMessage(val message: ToastMessage) : Effect()
        data class OpenMovieDetail(val navigator: Navigate) : Effect()
    }
}