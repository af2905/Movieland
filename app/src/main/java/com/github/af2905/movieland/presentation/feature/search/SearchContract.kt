package com.github.af2905.movieland.presentation.feature.search

import com.github.af2905.movieland.core.base.UiEffect
import com.github.af2905.movieland.core.base.UiState
import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.effect.ToastMessage
import com.github.af2905.movieland.core.common.model.Model
import com.github.af2905.movieland.core.common.model.item.LoadingItem
import com.github.af2905.movieland.core.common.model.item.SearchItem

class SearchContract {

    sealed class State(
        open val searchItem: SearchItem,
        open val list: List<Model>
    ) : UiState() {
        data class Loading(
            override val searchItem: SearchItem,
            override val list: List<Model> = listOf(LoadingItem())
        ) : State(searchItem, list)

        data class EmptyQuery(
            override val searchItem: SearchItem = SearchItem(),
            override val list: List<Model> = emptyList()
        ) : State(searchItem, list)

        data class Content(
            override val searchItem: SearchItem,
            override val list: List<Model>
        ) : State(searchItem, list)

        data class Error(
            override val searchItem: SearchItem,
            override val list: List<Model>,
            val e: Throwable?
        ) : State(searchItem, list)

        data class EmptyResult(
            override val searchItem: SearchItem,
            override val list: List<Model>
        ) : State(searchItem, list)
    }

    sealed class Effect : UiEffect() {

        data class ShowFailMessage(val message: ToastMessage) : Effect()
        data class OpenMovieDetail(val navigator: Navigate) : Effect()
    }
}