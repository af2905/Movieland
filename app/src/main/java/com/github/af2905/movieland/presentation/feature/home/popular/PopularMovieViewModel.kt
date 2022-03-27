package com.github.af2905.movieland.presentation.feature.home.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.domain.usecase.movies.GetPopularMovies
import com.github.af2905.movieland.domain.usecase.params.PopularMoviesParams
import com.github.af2905.movieland.helper.coroutine.CoroutineDispatcherProvider
import com.github.af2905.movieland.presentation.base.Container
import com.github.af2905.movieland.presentation.common.ErrorHandler
import com.github.af2905.movieland.presentation.common.effect.Navigate
import com.github.af2905.movieland.presentation.common.effect.ToastMessage
import com.github.af2905.movieland.presentation.feature.home.HomeNavigator
import com.github.af2905.movieland.presentation.feature.home.HomeRepository
import com.github.af2905.movieland.presentation.model.item.MovieItemVariant
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class PopularMovieViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies,
    private val homeRepository: HomeRepository,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    val container: Container<PopularMovieContract.State, PopularMovieContract.Effect> =
        Container(viewModelScope, PopularMovieContract.State.Content(isLoading = true))

    val items = container.state
        .filter { it is PopularMovieContract.State.Content }
        .map { (it as PopularMovieContract.State.Content).list }
        .asLiveData()

    init {
        loadData()
        viewModelScope.launch(coroutineDispatcherProvider.main()) {
            homeRepository.subscribeOnForceUpdate(this) { force -> if (force) refresh() }
        }
    }

    private fun loadData(forceUpdate: Boolean = false) {
        container.intent {
            try {
                getPopularMovies(PopularMoviesParams(forceUpdate = forceUpdate)).let {
                    container.reduce {
                        it.getOrThrow().let {
                            PopularMovieContract.State.Content(
                                isLoading = false,
                                list = it.map { item -> MovieItemVariant(item) },
                                error = null
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                container.reduce {
                    PopularMovieContract.State.Content(isLoading = false, error = e)
                }
                container.postEffect(
                    PopularMovieContract.Effect.ShowFailMessage(
                        ToastMessage(ErrorHandler.handleError(e))
                    )
                )
            }
        }
    }

    private fun refresh() = loadData(forceUpdate = true)

    fun openDetail(itemId: Int) = navigateToDetail(itemId)

    private fun navigateToDetail(itemId: Int) {
        container.intent {
            container.postEffect(PopularMovieContract.Effect.OpenMovieDetail(Navigate { navigator ->
                (navigator as HomeNavigator).forwardMovieDetail(itemId)
            }))
        }
    }
}