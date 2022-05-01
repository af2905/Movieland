package com.github.af2905.movieland.presentation.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.R
import com.github.af2905.movieland.domain.usecase.params.PopularMoviesParams
import com.github.af2905.movieland.domain.usecase.params.SearchMovieParams
import com.github.af2905.movieland.domain.usecase.search.GetPopularSearchQueries
import com.github.af2905.movieland.domain.usecase.search.GetSearchMovie
import com.github.af2905.movieland.helper.extension.empty
import com.github.af2905.movieland.helper.text.UiText
import com.github.af2905.movieland.presentation.base.Container
import com.github.af2905.movieland.presentation.common.ErrorHandler
import com.github.af2905.movieland.presentation.common.effect.Navigate
import com.github.af2905.movieland.presentation.common.effect.ToastMessage
import com.github.af2905.movieland.presentation.model.Model
import com.github.af2905.movieland.presentation.model.item.*
import com.github.af2905.movieland.presentation.model.item.SearchItem.Companion.TEXT_ENTERED_DEBOUNCE_MILLIS
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getSearchMovie: GetSearchMovie,
    private val getPopularSearchQueries: GetPopularSearchQueries
) : ViewModel() {

    val container: Container<SearchContract.State, SearchContract.Effect> =
        Container(viewModelScope, SearchContract.State.EmptyQuery())

    private val searchItem: StateFlow<SearchItem> = container.state
        .filter { it is SearchContract.State.Loading }
        .map { (it as SearchContract.State.Loading).searchItem }
        .stateIn(viewModelScope, SharingStarted.Lazily, SearchItem())

    init {
        viewModelScope.launch {
            searchItem.debounce(TEXT_ENTERED_DEBOUNCE_MILLIS).mapLatest(::handleQuery)
                .collect { state ->
                    container.reduce { state }
                    if (state is SearchContract.State.Error) {
                        showError(ErrorHandler.handleError(state.e))
                    }
                }
        }
    }

    private suspend fun handleQuery(searchItem: SearchItem): SearchContract.State {
        val result = if (searchItem.searchString.isEmpty()) {
            val queries = getPopularSearchQueries(PopularMoviesParams())
                .getOrDefault(emptyList())
            SearchContract.State.EmptyQuery(
                searchItem = searchItem,
                list = listOf<Model>(HeaderItemAlpha(R.string.search_popular_search_queries)) + queries
            )
        } else {
            getSearchMovie(SearchMovieParams(query = searchItem.searchString)).let { result ->
                if (result.isFailure) {
                    val error = result.exceptionOrNull()
                    SearchContract.State.Error(
                        searchItem = searchItem,
                        list = listOf(ErrorItem(errorMessage = error?.message.orEmpty())),
                        e = error
                    )
                } else {
                    result.getOrNull()?.movies.orEmpty().let { movies ->
                        if (movies.isEmpty()) SearchContract.State.EmptyResult(
                            searchItem = searchItem,
                            list = listOf(EmptyResultItem())
                        )
                        else SearchContract.State.Content(
                            searchItem = searchItem,
                            list = movies.map { MovieItemVariant(it) })
                    }
                }
            }
        }
        return result
    }

    fun searchTextChanged(text: String) {
        container.intent {
            container.reduce {
                SearchContract.State.Loading(
                    searchItem = SearchItem(
                        searchString = text,
                        clearText = text.isEmpty(),
                        deleteVisible = text.isNotEmpty()
                    )
                )
            }
        }
    }

    fun searchDeleteTextClicked() = searchTextChanged(String.empty)

    private fun showError(error: UiText) {
        container.intent {
            container.postEffect(SearchContract.Effect.ShowFailMessage(ToastMessage(error)))
        }
    }

    fun openDetail(itemId: Int) = navigateToDetail(itemId)

    private fun navigateToDetail(itemId: Int) {
        container.intent {
            container.postEffect(SearchContract.Effect.OpenMovieDetail(Navigate { navigator ->
                (navigator as SearchNavigator).forwardMovieDetail(itemId)
            }))
        }
    }
}