package com.github.af2905.movieland.search.presentation.result

data class SearchResultState(
    val query: String = ""
)

sealed interface SearchResultAction {
    data class UpdateQuery(val query: String) : SearchResultAction
    data object ClearQuery : SearchResultAction
    data object SubmitSearch : SearchResultAction
}

sealed interface SearchResultEffect {
    data class NavigateToResults(val query: String) : SearchResultEffect
}