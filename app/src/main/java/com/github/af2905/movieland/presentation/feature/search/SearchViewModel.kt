package com.github.af2905.movieland.presentation.feature.search

import androidx.lifecycle.*
import com.github.af2905.movieland.R
import com.github.af2905.movieland.domain.usecase.movies.GetPopularMovies
import com.github.af2905.movieland.domain.usecase.search.GetSearchMovie
import com.github.af2905.movieland.helper.coroutine.CoroutineDispatcherProvider
import com.github.af2905.movieland.presentation.base.Container
import com.github.af2905.movieland.presentation.model.Model
import com.github.af2905.movieland.presentation.model.item.EmptySpaceItem
import com.github.af2905.movieland.presentation.model.item.SearchItem
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getSearchMovie: GetSearchMovie,
    private val getPopularMovies: GetPopularMovies,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    val container: Container<SearchContract.State, SearchContract.Effect> =
        Container(viewModelScope, SearchContract.State.Loading)

    private val _items = MutableLiveData<MutableList<Model>>()
    val items: LiveData<MutableList<Model>> = _items

    private val emptySpaceSmall = EmptySpaceItem(R.dimen.default_margin_small)
    private val emptySpaceNormal = EmptySpaceItem(R.dimen.default_margin)
    private val emptySpaceMedium = EmptySpaceItem(R.dimen.default_margin_medium)
    private val emptySpaceBig = EmptySpaceItem(R.dimen.default_margin_big)

    private val queryFlow = MutableStateFlow("")

    var searchItem = MutableLiveData(SearchItem())

    private val _searchResult = MutableStateFlow<SearchContract.State>(SearchContract.State.EmptyQuery)
    val state: LiveData<SearchContract.State>
        get() = _searchResult.asLiveData(viewModelScope.coroutineContext)

 /*   init {
        launchUI {
            queryFlow
                .debounce(TEXT_ENTERED_DEBOUNCE_MILLIS)
                .onEach { _searchResult.value = SearchContract.State.Loading }
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

    private suspend fun handleQuery(query: String, scope: CoroutineScope): SearchContract.State {
        return if (query.isEmpty()) SearchContract.State.EmptyQuery
        else handleSearchMovie(query, scope)
    }

    private suspend fun handleSearchMovie(query: String, scope: CoroutineScope): SearchContract.State {

        val result = scope.iOAsync {
            getSearchMovie(SearchMovieParams(query = query))
        }.await().getOrThrow()

        return if (result.isFailure) {
            return SearchContract.State.Error(result.exceptionOrNull())
        } else {
            result.getOrNull()?.movies.orEmpty().let { movies ->
                if (movies.isEmpty()) SearchContract.State.EmptyResult
                else SearchContract.State.Success(movies)
            }
        }
    }

    fun handleMoviesList(state: SearchContract.State) {
        val list = mutableListOf<Model>()

        when (state) {
            is SearchContract.State.Success -> {
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
            is SearchContract.State.Error -> hideLoading(true)

            is SearchContract.State.EmptyResult -> {
                hideLoading(true)
                list.addAll(listOf(emptySpaceMedium, SimpleTextItem(R.string.search_empty_result)))
                launchUI {
                    _items.value = list
                    _items.notifyObserver()
                }
            }
            is SearchContract.State.EmptyQuery -> {
                launchUI {
                    hideLoading(false)
                    loadPopularMovies()
                    _items.value = list
                    _items.notifyObserver()
                }
            }
            is SearchContract.State.Loading -> showLoading()
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

    private suspend fun loadPopularMovies() {
*//*        getPopularMovies.invoke(PopularMoviesParams())
            .collect {
                val popularMovies = it.getOrDefault(emptyList())
                    .map { movieItem -> MovieItemVariant(movieItem) }

                if (popularMovies.isNotEmpty()) {
                    popularMovies.map { list ->
                        _items.value = mutableListOf(list, DividerItem())
                    }
                } else {
                    _items.value = mutableListOf()
                }
            }*//*
    }

    //fun openDetail(itemId: Int, position: Int) = navigator { forwardMovieDetail(itemId) }
*/

}