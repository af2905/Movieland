package com.github.af2905.movieland.movies.presentation.topRatedMovies

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
import com.github.af2905.movieland.core.shared.GetTopRatedMovies
import com.github.af2905.movieland.core.shared.TopRatedMoviesParams
import com.github.af2905.movieland.movies.presentation.MoviesNavigator
import com.github.af2905.movieland.movies.repository.MoviesForceUpdateRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopRatedMovieViewModel @Inject constructor(
    private val getTopRatedMovies: GetTopRatedMovies,
    private val forceUpdateRepository: MoviesForceUpdateRepository,
    private val getCachedMoviesByType: GetCachedMoviesByType,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    val container: Container<TopRatedMovieContract.State, TopRatedMovieContract.Effect> =
        Container(viewModelScope, TopRatedMovieContract.State.Init)

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
            CachedMoviesParams(type = MovieType.TOP_RATED)
        ).getOrDefault(emptyList())

        if (cachedMovies.isNotEmpty()) {
            container.reduce {
                TopRatedMovieContract.State.Content(list = cachedMovies.map { MovieV2Item(it) })
            }
        } else {
            container.reduce {
                TopRatedMovieContract.State.Loading()
            }
            val result =
                getTopRatedMovies(TopRatedMoviesParams(forceUpdate = forceUpdate)).getOrThrow()

            container.reduce {
                TopRatedMovieContract.State.Content(list = result.map { MovieV2Item(it) })
            }
        }
    }

    private suspend fun handleError(e: Exception) {
        val cachedMovies = getCachedMoviesByType(
            CachedMoviesParams(type = MovieType.TOP_RATED)
        ).getOrDefault(emptyList())

        if (cachedMovies.isNotEmpty()) {
            container.reduce {
                TopRatedMovieContract.State.Content(list = cachedMovies.map { MovieV2Item(it) })
            }
        } else {
            container.reduce {
                TopRatedMovieContract.State.Error(
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
            container.postEffect(TopRatedMovieContract.Effect.OpenMovieDetail(Navigate { navigator ->
                (navigator as MoviesNavigator).forwardToMovieDetailScreen(itemId)
            }))
        }
    }
}