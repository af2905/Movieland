package com.github.af2905.movieland.presentation.feature.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.R
import com.github.af2905.movieland.domain.usecase.movies.GetPopularMovies
import com.github.af2905.movieland.domain.usecase.params.PopularMoviesParams
import com.github.af2905.movieland.domain.usecase.params.SearchMovieParams
import com.github.af2905.movieland.domain.usecase.search.GetSearchMovie
import com.github.af2905.movieland.helper.Constant.MIN_TIME_REFRESH_DELAY
import com.github.af2905.movieland.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.helper.extension.notifyObserver
import com.github.af2905.movieland.presentation.base.BaseViewModel
import com.github.af2905.movieland.presentation.model.Model
import com.github.af2905.movieland.presentation.model.item.*
import com.github.af2905.movieland.presentation.model.item.SearchItem.Companion.TEXT_ENTERED_DEBOUNCE_MILLIS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getSearchMovie: GetSearchMovie,
    private val getPopularMovies: GetPopularMovies,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : BaseViewModel<SearchNavigator>(coroutineDispatcherProvider) {

    private val _items = MutableLiveData<MutableList<Model>>()
    val items: LiveData<MutableList<Model>> = _items

    private val emptySpaceSmall = EmptySpaceItem(R.dimen.default_margin_small)
    private val emptySpaceNormal = EmptySpaceItem(R.dimen.default_margin)
    private val emptySpaceMedium = EmptySpaceItem(R.dimen.default_margin_medium)
    private val emptySpaceBig = EmptySpaceItem(R.dimen.default_margin_big)

    private val queryFlow = MutableStateFlow("")

    var searchItem = MutableLiveData(SearchItem())

    private val _searchResult = MutableStateFlow<SearchResult>(SearchResult.EmptyQuery)
    val searchResult: LiveData<SearchResult>
        get() = _searchResult.asLiveData(viewModelScope.coroutineContext)

    init {
        launchUI {
            queryFlow
                .debounce(TEXT_ENTERED_DEBOUNCE_MILLIS)
                .onEach { _searchResult.value = SearchResult.Loading }
                .mapLatest { handleQuery(it, this) }
                .collect { state -> _searchResult.value = state }
        }
    }

    fun searchTextChanged(text: String) {
        searchItem.value = searchItem.value?.copy(clearText = false)
        searchItem.notifyObserver()
        queryFlow.value = text
    }

    fun searchDeleteTextClicked() {
        launchUI {
            searchItem.value = searchItem.value?.copy(clearText = true)
            searchItem.notifyObserver()
            queryFlow.value = ""
        }
    }

    private suspend fun handleQuery(query: String, scope: CoroutineScope): SearchResult {
        return if (query.isEmpty()) SearchResult.EmptyQuery
        else handleSearchMovie(query, scope)
    }

    private suspend fun handleSearchMovie(query: String, scope: CoroutineScope): SearchResult {

        val result = scope.iOAsync {
            getSearchMovie(SearchMovieParams(query = query))
        }.await().getOrThrow()

        return if (result.isFailure) {
            return SearchResult.ErrorResult(result.exceptionOrNull())
        } else {
            result.getOrNull()?.movies.orEmpty().let { movies ->
                if (movies.isEmpty()) SearchResult.EmptyResult
                else SearchResult.SuccessResult(movies)
            }
        }
    }

    fun handleMoviesList(state: SearchResult) {
        val list = mutableListOf<Model>()

        when (state) {
            is SearchResult.SuccessResult -> {
                hideLoadingWithDelay()
                list.add(emptySpaceMedium)
                list.add(DividerItem())
                state.result.map {
                    if (it is MovieItem) list.addAll(listOf(MovieItemVariant(it), DividerItem()))
                }
                launchUI {
                    _items.value = list
                    _items.notifyObserver()
                }
            }
            is SearchResult.ErrorResult -> hideLoading(true)

            is SearchResult.EmptyResult -> {
                hideLoading(true)
                list.addAll(listOf(emptySpaceMedium, SimpleTextItem(R.string.search_empty_result)))
                launchUI {
                    _items.value = list
                    _items.notifyObserver()
                }
            }
            is SearchResult.EmptyQuery -> {
                launchUI {
                    hideLoading(false)
                    val popular = loadPopularMoviesAsync(this)
                    list.addAll(popular.await().getOrDefault(emptyList()))
                    _items.value = list
                    _items.notifyObserver()
                }
            }
            is SearchResult.Loading -> showLoading()
        }
    }

    private fun showLoading() {
        if (searchItem.value?.loading == false) {
            searchItem.postValue(searchItem.value?.copy(loading = true, deleteVisible = false))
        }
    }

    private fun hideLoading(deleteVisible: Boolean) {
        if (searchItem.value?.loading == true) {
            searchItem.postValue(
                searchItem.value?.copy(loading = false, deleteVisible = deleteVisible)
            )
        }
    }

    private fun hideLoadingWithDelay() {
        launchUI {
            delay(MIN_TIME_REFRESH_DELAY)
            searchItem.postValue(
                searchItem.value?.copy(
                    loading = false, deleteVisible = queryFlow.value.isNotEmpty()
                )
            )
        }
    }

    private suspend fun loadPopularMoviesAsync(
        coroutineScope: CoroutineScope
    ): Deferred<Result<List<Model>>> {
        val deferredPopular: Deferred<Result<List<Model>>> = coroutineScope.iOAsync {
            val popularMovies =
                getPopularMovies(PopularMoviesParams()).getOrThrow().movies ?: listOf()

            if (popularMovies.isNotEmpty()) {
                mutableListOf<Model>().apply {
                    add(emptySpaceMedium)
                    add(HeaderItem(R.string.search_popular_search_queries))
                    add(emptySpaceMedium)
                    add(DividerItem())
                    popularMovies
                        .sortedByDescending { it.releaseYear }
                        .map { addAll(listOf(MovieItemVariant(it), DividerItem())) }
                }
            } else emptyList()
        }
        return deferredPopular
    }

    fun openDetail(itemId: Int, position: Int) = navigator { forwardMovieDetail(itemId) }

}