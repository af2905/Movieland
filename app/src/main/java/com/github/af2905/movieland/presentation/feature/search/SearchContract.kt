package com.github.af2905.movieland.presentation.feature.search

import com.github.af2905.movieland.presentation.base.UiEffect
import com.github.af2905.movieland.presentation.base.UiState
import com.github.af2905.movieland.presentation.common.effect.Navigate
import com.github.af2905.movieland.presentation.common.effect.ToastMessage
import com.github.af2905.movieland.presentation.model.Model
import com.github.af2905.movieland.presentation.model.item.LoadingItem
import com.github.af2905.movieland.presentation.model.item.SearchItem

class SearchContract {

    sealed class State : UiState() {
        data class Loading(
            val searchItem: SearchItem,
            val list: List<Model> = listOf(LoadingItem())
        ) : State()

        data class EmptyQuery(
            val searchItem: SearchItem = SearchItem(),
            val list: List<Model> = emptyList()
        ) : State()

        data class Content(
            val searchItem: SearchItem,
            val list: List<Model>
        ) : State()

        data class Error(val searchItem: SearchItem, val list: List<Model>, val e: Throwable?) :
            State()

        data class EmptyResult(val searchItem: SearchItem, val list: List<Model>) : State()

        fun searchItem(): SearchItem = when (this) {
            is Loading -> this.searchItem
            is EmptyQuery -> this.searchItem
            is Content -> this.searchItem
            is Error -> this.searchItem
            is EmptyResult -> this.searchItem
        }

        fun list(): List<Model> = when (this) {
            is Loading -> this.list
            is EmptyQuery -> this.list
            is Content -> this.list
            is Error -> this.list
            is EmptyResult -> this.list
        }
    }

    sealed class Effect : UiEffect() {

        data class ShowFailMessage(val message: ToastMessage) : Effect()
        data class OpenMovieDetail(val navigator: Navigate) : Effect()
    }
}