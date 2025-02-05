package com.github.af2905.movieland.search.presentation.search

data class SearchState(
    val query: String = ""
)

sealed interface SearchAction {
    data class UpdateQuery(val query: String) : SearchAction
    data object ClearQuery : SearchAction
    data object SubmitSearch : SearchAction
}

sealed interface SearchEffect {
    data class NavigateToResults(val query: String) : SearchEffect
}