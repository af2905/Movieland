package com.github.af2905.movieland.presentation.feature.home.upcoming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.domain.usecase.movies.GetUpcomingMovies
import com.github.af2905.movieland.domain.usecase.params.UpcomingMoviesParams
import com.github.af2905.movieland.helper.coroutine.CoroutineDispatcherProvider
import com.github.af2905.movieland.helper.text.UiText
import com.github.af2905.movieland.presentation.base.Container
import com.github.af2905.movieland.presentation.common.effect.Navigate
import com.github.af2905.movieland.presentation.common.effect.ToastMessage
import com.github.af2905.movieland.presentation.feature.home.HomeContract
import com.github.af2905.movieland.presentation.feature.home.HomeNavigator
import com.github.af2905.movieland.presentation.feature.home.HomeRepository
import com.github.af2905.movieland.presentation.model.Model
import com.github.af2905.movieland.presentation.model.item.DividerItem
import com.github.af2905.movieland.presentation.model.item.MovieItem
import com.github.af2905.movieland.presentation.model.item.MovieItemVariant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpcomingMovieViewModel @Inject constructor(
    private val getUpcomingMovies: GetUpcomingMovies,
    private val homeRepository: HomeRepository,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    val container: Container<HomeContract.State, HomeContract.Effect> =
        Container(viewModelScope, HomeContract.State.Loading())

    private val _items = MutableStateFlow<List<Model>>(listOf())
    val items = _items.asStateFlow()

    init {
        loadData()
        viewModelScope.launch(coroutineDispatcherProvider.main()) {
            homeRepository.subscribeOnForceUpdate(this) { force -> if (force) refresh() }
        }
    }

    private fun loadData(forceUpdate: Boolean = false) {
        container.intent {
            container.reduce { HomeContract.State.Loading(items.value) }
            try {
                getUpcomingMovies(UpcomingMoviesParams(forceUpdate = forceUpdate)).let {
                    container.reduce {
                        it.getOrThrow().let {
                            if (it.isEmpty()) HomeContract.State.EmptyResult
                            else HomeContract.State.Success(it)
                        }
                    }
                }
            } catch (e: Exception) {
                container.reduce { HomeContract.State.Error(e) }
            }
        }
    }

    fun updateData(movies: List<Model>) {
        val list = mutableListOf<Model>()
        movies.map { model ->
            list.addAll(
                listOf(MovieItemVariant(model as MovieItem), DividerItem())
            )
        }
        _items.value = list
    }

    private fun refresh() = loadData(forceUpdate = true)

    fun openDetail(itemId: Int) = navigateToDetail(itemId)

    private fun navigateToDetail(itemId: Int) {
        container.intent {
            container.postEffect(HomeContract.Effect.OpenMovieDetail(Navigate { navigator ->
                (navigator as HomeNavigator).forwardMovieDetail(itemId)
            }))
        }

    }

    fun showError(error: UiText) {
        container.intent {
            container.postEffect(HomeContract.Effect.ShowFailMessage(ToastMessage(error)))
        }
    }
}