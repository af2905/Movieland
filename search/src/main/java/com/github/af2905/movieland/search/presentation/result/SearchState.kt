package com.github.af2905.movieland.search.presentation.result

import com.github.af2905.movieland.core.data.database.entity.SearchMultiResult

data class SearchState(
    val query: String = "",
    val searchResult: List<SearchMultiResult> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val popularSearches: List<String> = emptyList()
)


sealed interface SearchAction {
    data class UpdateQuery(val query: String) : SearchAction
    data object ClearQuery : SearchAction
    data class OpenMovieDetail(val movieId: Int) : SearchAction
    data class OpenTvShowDetail(val tvShowId: Int) : SearchAction
    data class OpenPersonDetail(val personId: Int) : SearchAction
}

sealed interface SearchEffect {
    data class NavigateToMovieDetail(val movieId: Int) : SearchEffect
    data class NavigateToTvShowDetail(val tvShowId: Int) : SearchEffect
    data class NavigateToPersonDetail(val personId: Int) : SearchEffect
}