package com.github.af2905.movieland.presentation.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.R
import com.github.af2905.movieland.domain.usecase.movies.GetPopularMovies
import com.github.af2905.movieland.domain.usecase.params.PopularMoviesParams
import com.github.af2905.movieland.domain.usecase.params.SearchMovieParams
import com.github.af2905.movieland.domain.usecase.search.GetSearchMovie
import com.github.af2905.movieland.helper.extension.empty
import com.github.af2905.movieland.helper.text.UiText
import com.github.af2905.movieland.presentation.base.Container
import com.github.af2905.movieland.presentation.common.effect.Navigate
import com.github.af2905.movieland.presentation.common.effect.ToastMessage
import com.github.af2905.movieland.presentation.model.Model
import com.github.af2905.movieland.presentation.model.item.*
import com.github.af2905.movieland.presentation.model.item.SearchItem.Companion.TEXT_ENTERED_DEBOUNCE_MILLIS
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getSearchMovie: GetSearchMovie,
    private val getPopularMovies: GetPopularMovies
) : ViewModel() {

    val container: Container<SearchContract.State, SearchContract.Effect> =
        Container(viewModelScope, SearchContract.State.Loading())

    private val _items = MutableStateFlow(emptyList<Model>())
    val items = _items.asStateFlow()

    private val _emptySpaceSmall = EmptySpaceItem(R.dimen.default_margin_small)
    private val _emptySpaceNormal = EmptySpaceItem(R.dimen.default_margin)
    private val _emptySpaceMedium = EmptySpaceItem(R.dimen.default_margin_medium)
    private val _emptySpaceBig = EmptySpaceItem(R.dimen.default_margin_big)

    private val _queryFlow = MutableStateFlow(String.empty)

    private var _searchItem = MutableStateFlow(SearchItem())
    val searchItem = _searchItem.asStateFlow()

    private val _popularMovies = MutableStateFlow(listOf<MovieItem>())
    private val _popularMoviesHeader = HeaderItem(R.string.search_popular_search_queries)

    private val _notFoundItem = NotFoundItem(R.string.search_empty_result)

    init {
        container.intent {
            _popularMovies.tryEmit(getPopularMovies(PopularMoviesParams()).getOrDefault(emptyList()))
            _queryFlow
                .debounce(TEXT_ENTERED_DEBOUNCE_MILLIS)
                .mapLatest(::handleQuery)
                .collect { state -> container.reduce { state } }
        }
    }

    private fun handleQuery(query: String): SearchContract.State {
        return if (query.isEmpty()) SearchContract.State.EmptyQuery(_popularMovies.value)
        else SearchContract.State.Loading(query)
    }

    fun searchTextChanged(text: String) {
        _queryFlow.tryEmit(text)
        _searchItem.tryEmit(
            _searchItem.value.copy(clearText = text.isEmpty(), deleteVisible = text.isNotEmpty())
        )
    }

    fun searchDeleteTextClicked() = searchTextChanged(String.empty)

    fun handleSearchMovie(query: String) {
        container.intent {
            getSearchMovie(SearchMovieParams(query = query)).let { result ->
                if (result.isFailure) {
                    SearchContract.State.Error(result.exceptionOrNull())
                } else {
                    result.getOrNull()?.movies.orEmpty().let { movies ->
                        if (movies.isEmpty()) container.reduce { SearchContract.State.EmptyResult }
                        else container.reduce { SearchContract.State.Success(movies) }
                    }
                }
            }
        }
    }

    fun handleSuccess(movies: List<Model>) {
        showMovieList(movies)
    }

    fun handleEmptyQuery(movies: List<Model>) {
        val list = mutableListOf<Model>().apply {
            add(_popularMoviesHeader)
        }
        movies.map { model -> list.addAll(listOf(MovieItemVariant(model as MovieItem))) }
        _items.tryEmit(list)
    }

    fun handleEmptyResult() {
        _items.tryEmit(listOf(_notFoundItem))
    }

    private fun showMovieList(movies: List<Model>) {
        val list = mutableListOf<Model>()
        movies.map { model -> list.addAll(listOf(MovieItemVariant(model as MovieItem))) }
        _items.tryEmit(list)
    }

    fun showError(error: UiText) {
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