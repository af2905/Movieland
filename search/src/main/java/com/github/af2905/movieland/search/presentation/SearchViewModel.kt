package com.github.af2905.movieland.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.base.Container
import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.model.Model
import com.github.af2905.movieland.core.common.model.item.EmptyResultItem
import com.github.af2905.movieland.core.common.model.item.ErrorItem
import com.github.af2905.movieland.core.common.model.item.HeaderItem
import com.github.af2905.movieland.core.common.model.item.SearchItem
import com.github.af2905.movieland.core.common.model.item.SearchItem.Companion.TEXT_ENTERED_DEBOUNCE_MILLIS
import com.github.af2905.movieland.core.data.MediaType
import com.github.af2905.movieland.search.R
import com.github.af2905.movieland.search.SearchNavigator
import com.github.af2905.movieland.search.domain.params.PopularMoviesParams
import com.github.af2905.movieland.search.domain.params.SearchMultiParams
import com.github.af2905.movieland.search.domain.usecase.GetPopularSearchQueries
import com.github.af2905.movieland.search.domain.usecase.GetSearchMovie
import com.github.af2905.movieland.search.domain.usecase.GetSearchMulti
import com.github.af2905.movieland.util.extension.empty
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getSearchMovie: GetSearchMovie,
    private val getSearchMulti: GetSearchMulti,
    private val getPopularSearchQueries: GetPopularSearchQueries
) : ViewModel() {

    val container: Container<SearchContract.State, SearchContract.Effect> =
        Container(viewModelScope, SearchContract.State.EmptyQuery())

    private val query = MutableStateFlow(String.empty)

    init {
        container.intent {
            container.reduce {
                SearchContract.State.Loading(SearchItem())
            }
            query.debounce(TEXT_ENTERED_DEBOUNCE_MILLIS)
                .mapLatest(::handleQuery)
                .collect { state -> container.reduce { state } }
        }
    }

    private suspend fun handleQuery(text: String): SearchContract.State {
        val result = if (text.isEmpty()) {
            val queries = getPopularSearchQueries(PopularMoviesParams()).getOrDefault(emptyList())
            SearchContract.State.EmptyQuery(
                searchItem = container.state.value.searchItem.copy(searchString = query.value),
                list = listOf<Model>(HeaderItem(R.string.search_popular_search_queries)) + queries
            )
        } else {
            val result = getSearchMulti(SearchMultiParams(query = text))
            if (result.isFailure) {
                val error = result.exceptionOrNull()
                SearchContract.State.Error(
                    searchItem = container.state.value.searchItem.copy(searchString = query.value),
                    list = listOf(ErrorItem(errorMessage = error?.message.orEmpty())),
                    e = error
                )
            } else {
                val multiResult = result.getOrNull().orEmpty()
                if (multiResult.isEmpty()) SearchContract.State.EmptyResult(
                    searchItem = container.state.value.searchItem.copy(searchString = query.value),
                    list = listOf(EmptyResultItem())
                )
                else SearchContract.State.Content(
                    searchItem = container.state.value.searchItem.copy(searchString = query.value),
                    list = multiResult
                )
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
        query.tryEmit(text)
    }

    fun searchDeleteTextClicked() = searchTextChanged(String.empty)

    fun update() {
        val searchItem = container.state.value.searchItem
        searchTextChanged(text = searchItem.searchString)
    }

    fun openDetail(itemId: Int, mediaType: MediaType) {
        container.intent {
            container.postEffect(SearchContract.Effect.OpenDetail(Navigate { navigator ->
                val searchNavigator = navigator as SearchNavigator
                when(mediaType) {
                    MediaType.MOVIE -> searchNavigator.forwardMovieDetail(itemId)
                    MediaType.PERSON -> searchNavigator.forwardPersonDetail(itemId)
                    else -> Unit
                }
            }))
        }
    }
}