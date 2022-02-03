package com.github.af2905.movieland.presentation.feature.search

import com.github.af2905.movieland.presentation.base.UiEffect
import com.github.af2905.movieland.presentation.base.UiState
import com.github.af2905.movieland.presentation.common.effect.ToastMessage
import com.github.af2905.movieland.presentation.model.Model

class SearchContract {

    sealed class State : UiState(){
        object Loading : State() {
            override fun toString(): String = "State.Loading"
        }

        data class Success(val result: List<Model>) : State()
        data class Error(val e: Throwable?) : State()
        object EmptyResult : State()
        object EmptyQuery : State()
    }

    sealed class Effect : UiEffect() {

        data class FailMessage(val message: ToastMessage) : Effect()
        data class OpenMovieDetail(val id: Int) : Effect()
    }
}