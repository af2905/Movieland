package com.github.af2905.movieland.home.presentation.upcomingMovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.base.Container
import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.core.common.model.item.ErrorItem
import com.github.af2905.movieland.core.common.model.item.MovieItemV2
import com.github.af2905.movieland.core.data.database.entity.MovieType
import com.github.af2905.movieland.home.domain.params.CachedMoviesParams
import com.github.af2905.movieland.home.domain.params.UpcomingMoviesParams
import com.github.af2905.movieland.home.domain.usecase.GetCachedMoviesByType
import com.github.af2905.movieland.home.domain.usecase.GetUpcomingMovies
import com.github.af2905.movieland.home.presentation.HomeNavigator
import com.github.af2905.movieland.home.repository.HomeRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpcomingMovieViewModel @Inject constructor(
    private val getUpcomingMovies: GetUpcomingMovies,
    private val homeRepository: HomeRepository,
    private val getCachedMoviesByType: GetCachedMoviesByType,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    val container: Container<UpcomingMovieContract.State, UpcomingMovieContract.Effect> =
        Container(viewModelScope, UpcomingMovieContract.State.Init)

    init {
        viewModelScope.launch(coroutineDispatcherProvider.main()) {
            homeRepository.subscribeOnForceUpdate(this) { force -> if (force) refresh() }
        }
        savedLoadData()
    }

    private fun savedLoadData(forceUpdate: Boolean = false) {
        container.intent {
            try {
                loadData(forceUpdate = forceUpdate)
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private suspend fun loadData(forceUpdate: Boolean) {
        val cachedMovies = getCachedMoviesByType(
            CachedMoviesParams(type = MovieType.UPCOMING)
        ).getOrDefault(emptyList())

        if (cachedMovies.isNotEmpty()) {
            container.reduce {
                UpcomingMovieContract.State.Content(list = cachedMovies.map { MovieItemV2(it) })
            }
        } else {
            container.reduce {
                UpcomingMovieContract.State.Loading()
            }
            val result =
                getUpcomingMovies(UpcomingMoviesParams(forceUpdate = forceUpdate)).getOrThrow()

            container.reduce {
                UpcomingMovieContract.State.Content(list = result.map { MovieItemV2(it) })
            }
        }
    }

    private suspend fun handleError(e: Exception) {
        val cachedMovies = getCachedMoviesByType(
            CachedMoviesParams(type = MovieType.UPCOMING)
        ).getOrDefault(emptyList())

        if (cachedMovies.isNotEmpty()) {
            container.reduce {
                UpcomingMovieContract.State.Content(list = cachedMovies.map { MovieItemV2(it) })
            }
        } else {
            container.reduce {
                UpcomingMovieContract.State.Error(
                    list = listOf(
                        ErrorItem(
                            errorMessage = e.message.orEmpty(),
                            errorButtonVisible = false
                        )
                    ),
                    e = e
                )
            }
        }
    }

    private fun refresh() = savedLoadData(forceUpdate = true)

    fun openDetail(itemId: Int) {
        container.intent {
            container.postEffect(UpcomingMovieContract.Effect.OpenMovieDetail(Navigate { navigator ->
                (navigator as HomeNavigator).forwardMovieDetail(itemId)
            }))
        }
    }
}