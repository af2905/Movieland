package com.github.af2905.movieland.presentation.feature.detail.moviedetail

import com.github.af2905.movieland.presentation.base.UiEffect
import com.github.af2905.movieland.presentation.base.UiState
import com.github.af2905.movieland.presentation.common.effect.ToastMessage
import com.github.af2905.movieland.presentation.model.Model

class MovieDetailContract {

    sealed class State : UiState() {

        data class Loading(val result: List<Model> = emptyList()) : State()
        data class Success(val result: List<Model>) : State()
        data class Error(val e: Throwable?) : State()
        object EmptyResult : State()

    }

    sealed class Effect : UiEffect() {

        data class FailMessage(val message: ToastMessage) : Effect()
        data class OpenMovieDetail(val id: Int) : Effect()
        data class OpenActorDetail(val id: Int) : Effect()

    }
}