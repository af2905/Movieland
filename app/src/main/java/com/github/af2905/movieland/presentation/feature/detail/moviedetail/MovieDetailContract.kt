package com.github.af2905.movieland.presentation.feature.detail.moviedetail

import com.github.af2905.movieland.presentation.base.UiEffect
import com.github.af2905.movieland.presentation.base.UiState
import com.github.af2905.movieland.presentation.common.effect.Navigate
import com.github.af2905.movieland.presentation.common.effect.ToastMessage
import com.github.af2905.movieland.presentation.feature.detail.moviedetail.item.MovieDetailsItem
import com.github.af2905.movieland.presentation.model.Model
import com.github.af2905.movieland.presentation.model.item.ErrorItem

class MovieDetailContract {

    sealed class State : UiState() {

        object Loading : State() {
            override fun toString(): String = javaClass.simpleName
        }

        data class Content(val movieDetailsItem: MovieDetailsItem, val list: List<Model>) : State()
        data class Error(val errorItem: ErrorItem = ErrorItem(), val e: Throwable?) : State()

        fun toContent(): Content? = if (this is Content) this else null
        fun toError(): Error? = if (this is Error) this else null
    }

    sealed class Effect : UiEffect() {

        data class ShowFailMessage(val message: ToastMessage) : Effect()
        data class OpenMovieDetail(val navigator: Navigate) : Effect()
        data class OpenActorDetail(val navigator: Navigate) : Effect()
    }
}