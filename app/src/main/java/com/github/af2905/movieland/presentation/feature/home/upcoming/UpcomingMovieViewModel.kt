package com.github.af2905.movieland.presentation.feature.home.upcoming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.base.Container
import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.core.common.model.item.ErrorItem
import com.github.af2905.movieland.core.common.model.item.MovieItemVariant
import com.github.af2905.movieland.domain.usecase.movies.GetUpcomingMovies
import com.github.af2905.movieland.domain.usecase.params.UpcomingMoviesParams
import com.github.af2905.movieland.presentation.feature.home.HomeNavigator
import com.github.af2905.movieland.presentation.feature.home.HomeRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpcomingMovieViewModel @Inject constructor(
    private val getUpcomingMovies: GetUpcomingMovies,
    private val homeRepository: HomeRepository,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    val container: Container<UpcomingMovieContract.State, UpcomingMovieContract.Effect> =
        Container(viewModelScope, UpcomingMovieContract.State.Init())

    init {
        loadData()
        viewModelScope.launch(coroutineDispatcherProvider.main()) {
            homeRepository.subscribeOnForceUpdate(this) { force -> if (force) refresh() }
        }
    }

    private fun loadData(forceUpdate: Boolean = false) {
        container.intent {
            container.reduce {
                UpcomingMovieContract.State.Init(list = this.list)
            }
            val result = getUpcomingMovies(UpcomingMoviesParams(forceUpdate = forceUpdate))
            if (result.isFailure) {
                val error = result.exceptionOrNull()
                container.reduce {
                    UpcomingMovieContract.State.Error(
                        list = listOf(
                            ErrorItem(
                                errorMessage = error?.message.orEmpty(),
                                errorButtonVisible = false
                            )
                        ),
                        e = error
                    )
                }
            } else {
                val movies = result.getOrNull().orEmpty()
                container.reduce {
                    UpcomingMovieContract.State.Content(
                        list = movies.map { MovieItemVariant(it) }
                    )
                }
            }
        }
    }

    private fun refresh() = loadData(forceUpdate = true)

    fun openDetail(itemId: Int) = navigateToDetail(itemId)

    private fun navigateToDetail(itemId: Int) {
        container.intent {
            container.postEffect(UpcomingMovieContract.Effect.OpenMovieDetail(Navigate { navigator ->
                (navigator as HomeNavigator).forwardMovieDetail(itemId)
            }))
        }

    }
}