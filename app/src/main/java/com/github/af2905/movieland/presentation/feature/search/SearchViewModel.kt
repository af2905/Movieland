package com.github.af2905.movieland.presentation.feature.search

import androidx.lifecycle.*
import com.github.af2905.movieland.R
import com.github.af2905.movieland.domain.usecase.movies.GetPopularMovies
import com.github.af2905.movieland.domain.usecase.params.PopularMoviesParams
import com.github.af2905.movieland.domain.usecase.params.SearchMovieParams
import com.github.af2905.movieland.domain.usecase.search.GetSearchMovie
import com.github.af2905.movieland.helper.coroutine.CoroutineDispatcherProvider
import com.github.af2905.movieland.helper.extension.empty
import com.github.af2905.movieland.helper.text.UiText
import com.github.af2905.movieland.presentation.base.Container
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
    private val getPopularMovies: GetPopularMovies,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    val container: Container<SearchContract.State, SearchContract.Effect> =
        Container(viewModelScope, SearchContract.State.Loading())

    private val _items = MutableStateFlow(emptyList<Model>())
    val items = _items.asStateFlow()

    private val emptySpaceSmall = EmptySpaceItem(R.dimen.default_margin_small)
    private val emptySpaceNormal = EmptySpaceItem(R.dimen.default_margin)
    private val emptySpaceMedium = EmptySpaceItem(R.dimen.default_margin_medium)
    private val emptySpaceBig = EmptySpaceItem(R.dimen.default_margin_big)

    private val queryFlow = MutableStateFlow(String.empty)

    var searchItem = MutableLiveData(SearchItem())

    private val _searchResult =
        MutableStateFlow<SearchContract.State>(SearchContract.State.EmptyQuery())
    val state: LiveData<SearchContract.State>
        get() = _searchResult.asLiveData(viewModelScope.coroutineContext)

    init {
        viewModelScope.launch(coroutineDispatcherProvider.main()) {
            queryFlow
                .debounce(TEXT_ENTERED_DEBOUNCE_MILLIS)
                .onEach { _searchResult.value = SearchContract.State.Loading() }
                .mapLatest(::handleQuery)
                .collect { state ->
                    when (state) {
                        is SearchContract.State.EmptyQuery -> loadPopularMovies()
                        is SearchContract.State.Loading -> handleSearchMovie(state.query)
                        else -> Unit
                    }
                }
        }
    }

    private fun handleQuery(query: String): SearchContract.State {
        return if (query.isEmpty()) SearchContract.State.EmptyQuery()
        else SearchContract.State.Loading(query)
    }

    private fun loadPopularMovies() {
        container.intent {
            container.reduce { SearchContract.State.Loading() }
            try {
                getPopularMovies(PopularMoviesParams()).let {
                    container.reduce {
                        it.getOrDefault(emptyList()).let { movies ->
                            SearchContract.State.EmptyQuery(movies)
                        }
                    }
                }
            } catch (e: Exception) {
                container.reduce { SearchContract.State.Error(e) }
            }
        }
    }

    fun searchTextChanged(text: String) {
/*        searchItem.value = searchItem.value?.copy(clearText = false)
        searchItem.notifyObserver()*/
        queryFlow.tryEmit(text)
    }

    fun searchDeleteTextClicked() {
/*        launchUI {
            searchItem.value = searchItem.value?.copy(clearText = true)
            searchItem.notifyObserver()*/
        queryFlow.tryEmit(String.empty)
        // }
    }

    private fun handleSearchMovie(query: String) {
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
        showMovieList(movies)
    }

    fun handleEmptyResult() {
        container.intent {
            _items.tryEmit(listOf(emptySpaceMedium, SimpleTextItem(R.string.search_empty_result)))
        }
    }

    fun handleLoading() {

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