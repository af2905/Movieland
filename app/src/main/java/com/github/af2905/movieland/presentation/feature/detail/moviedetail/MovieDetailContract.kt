package com.github.af2905.movieland.presentation.feature.detail.moviedetail

import com.github.af2905.movieland.presentation.base.UiEffect
import com.github.af2905.movieland.presentation.base.UiState
import com.github.af2905.movieland.presentation.common.effect.Navigate
import com.github.af2905.movieland.presentation.common.effect.ToastMessage
import com.github.af2905.movieland.presentation.feature.detail.moviedetail.item.MovieDetailsItem
import com.github.af2905.movieland.presentation.model.Model

class MovieDetailContract {

    sealed class State : UiState() {

        data class Content(
            val isLoading: Boolean = false,
            val movieDetailsItem: MovieDetailsItem? = null,
            val list: List<Model> = emptyList(),
            val error: Throwable? = null
        ) : State()

        //data class Error(val e: Throwable?) : State()
        //object EmptyResult : State()
    }

    sealed class Effect : UiEffect() {

        data class ShowFailMessage(val message: ToastMessage) : Effect()
        data class OpenMovieDetail(val navigator: Navigate) : Effect()
        data class OpenActorDetail(val navigator: Navigate) : Effect()
        data class MoveToBackScreen(val navigator: Navigate) : Effect()
    }
}