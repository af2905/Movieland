package com.github.af2905.movieland.movies.presentation.popularMovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.base.Container
import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.core.common.model.item.ErrorItem
import com.github.af2905.movieland.core.common.model.item.MovieV2Item
import com.github.af2905.movieland.core.data.database.entity.MovieType
import com.github.af2905.movieland.core.shared.CachedMoviesParams
import com.github.af2905.movieland.core.shared.GetCachedMoviesByType
import com.github.af2905.movieland.core.shared.GetPopularMovies
import com.github.af2905.movieland.core.shared.PopularMoviesParams
import com.github.af2905.movieland.movies.presentation.MoviesNavigator
import com.github.af2905.movieland.movies.repository.MoviesForceUpdateRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class PopularMovieViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies,
    private val forceUpdateRepository: MoviesForceUpdateRepository,
    private val getCachedMoviesByType: GetCachedMoviesByType,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    val container: Container<PopularMovieContract.State, PopularMovieContract.Effect> =
        Container(viewModelScope, PopularMovieContract.State.Init)

    init {
        viewModelScope.launch(coroutineDispatcherProvider.main()) {
            forceUpdateRepository.subscribeOnForceUpdate(this) { force -> if (force) refresh() }
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
            CachedMoviesParams(type = MovieType.POPULAR)
        ).getOrDefault(emptyList())

        if (cachedMovies.isNotEmpty()) {
            container.reduce {
                PopularMovieContract.State.Content(list = cachedMovies.map { MovieV2Item(it) })
            }
        } else {
            container.reduce {
                PopularMovieContract.State.Loading()
            }
            val result =
                getPopularMovies(PopularMoviesParams(forceUpdate = forceUpdate)).getOrThrow()

            container.reduce {
                PopularMovieContract.State.Content(list = result.map { MovieV2Item(it) })
            }
        }
    }

    private fun refresh() = savedLoadData(forceUpdate = true)

    private suspend fun handleError(e: Exception) {
        val cachedMovies = getCachedMoviesByType(
            CachedMoviesParams(type = MovieType.POPULAR)
        ).getOrDefault(emptyList())

        if (cachedMovies.isNotEmpty()) {
            container.reduce {
                PopularMovieContract.State.Content(list = cachedMovies.map { MovieV2Item(it) })
            }
        } else {
            container.reduce {
                PopularMovieContract.State.Error(
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

    fun openDetail(itemId: Int) {
        container.intent {
            container.postEffect(PopularMovieContract.Effect.OpenMovieDetail(Navigate { navigator ->
                (navigator as MoviesNavigator).forwardToMovieDetailScreen(itemId)
            }))
        }
    }
}