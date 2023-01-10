package com.github.af2905.movieland.home.presentation

import com.github.af2905.movieland.core.base.UiEffect
import com.github.af2905.movieland.core.base.UiState
import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.effect.ToastMessage
import com.github.af2905.movieland.core.common.model.Model
import com.github.af2905.movieland.core.common.model.item.ErrorItem
import com.github.af2905.movieland.home.presentation.item.HomePlaceholderItem

class HomeContract {

    sealed class State(open val list: List<Model>) : UiState() {

        data class Loading(
            override val list: List<Model> = listOf(HomePlaceholderItem())
        ) : State(list)

        data class Empty(
            override val list: List<Model> = listOf(ErrorItem())
        ) : State(list)

        data class Content(override val list: List<Model>) : State(list)
    }

    sealed class Effect : UiEffect() {

        data class ShowFailMessage(val message: ToastMessage) : Effect()
        data class OpenMovieDetail(val navigator: Navigate) : Effect()
        data class OpenPersonDetail(val navigator: Navigate) : Effect()
        data class OpenMovies(val navigator: Navigate) : Effect()
        data class OpenPeople(val navigator: Navigate) : Effect()
        data class OpenTvShows(val navigator: Navigate) : Effect()
        object FinishRefresh : Effect()
    }
}