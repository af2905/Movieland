package com.github.af2905.movieland.search.presentation.search

data class SearchState(
    val query: String = ""
)

sealed interface SearchAction {
    data object StartSearch : SearchAction
}

sealed interface SearchEffect {
    data object NavigateToResults : SearchEffect
}