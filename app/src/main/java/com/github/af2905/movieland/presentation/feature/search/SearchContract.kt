package com.github.af2905.movieland.presentation.feature.search

import com.github.af2905.movieland.helper.extension.empty
import com.github.af2905.movieland.presentation.base.UiEffect
import com.github.af2905.movieland.presentation.base.UiState
import com.github.af2905.movieland.presentation.common.effect.Navigate
import com.github.af2905.movieland.presentation.common.effect.ToastMessage
import com.github.af2905.movieland.presentation.model.Model

class SearchContract {

    sealed class State : UiState() {
        data class Loading (val query: String = String.empty) : State()
        data class EmptyQuery(val list: List<Model> = emptyList()) : State()
        data class Success(val list: List<Model>) : State()
        data class Error(val e: Throwable?) : State()
        object EmptyResult : State()
    }

    sealed class Effect : UiEffect() {

        data class ShowFailMessage(val message: ToastMessage) : Effect()
        data class OpenMovieDetail(val navigator: Navigate) : Effect()
    }
}