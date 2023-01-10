package com.github.af2905.movieland.home.presentation.movies.nowPlayingMovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.base.Container
import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.core.common.model.item.ErrorItem
import com.github.af2905.movieland.core.common.model.item.MovieV2Item
import com.github.af2905.movieland.core.data.database.entity.MovieType
import com.github.af2905.movieland.home.domain.params.CachedMoviesParams
import com.github.af2905.movieland.home.domain.params.NowPlayingMoviesParams
import com.github.af2905.movieland.home.domain.usecase.GetCachedMoviesByType
import com.github.af2905.movieland.home.domain.usecase.GetNowPlayingMovies
import com.github.af2905.movieland.home.presentation.HomeNavigator
import com.github.af2905.movieland.home.repository.ForceUpdateRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class NowPlayingMovieViewModel @Inject constructor(
    private val getNowPlayingMovies: GetNowPlayingMovies,
    private val forceUpdateRepository: ForceUpdateRepository,
    private val getCachedMoviesByType: GetCachedMoviesByType,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    val container: Container<NowPlayingMovieContract.State, NowPlayingMovieContract.Effect> =
        Container(viewModelScope, NowPlayingMovieContract.State.Init)

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
            CachedMoviesParams(type = MovieType.NOW_PLAYING)
        ).getOrDefault(emptyList())

        if (cachedMovies.isNotEmpty()) {
            container.reduce {
                NowPlayingMovieContract.State.Content(list = cachedMovies.map { MovieV2Item(it) })
            }
        } else {
            container.reduce {
                NowPlayingMovieContract.State.Loading()
            }
            val result =
                getNowPlayingMovies(NowPlayingMoviesParams(forceUpdate = forceUpdate)).getOrThrow()

            container.reduce {
                NowPlayingMovieContract.State.Content(list = result.map { MovieV2Item(it) })
            }
        }
    }

    private suspend fun handleError(e: Exception) {
        val cachedMovies = getCachedMoviesByType(
            CachedMoviesParams(type = MovieType.NOW_PLAYING)
        ).getOrDefault(emptyList())

        if (cachedMovies.isNotEmpty()) {
            container.reduce {
                NowPlayingMovieContract.State.Content(
                    list = cachedMovies.map { MovieV2Item(it) }
                )
            }
        } else {
            container.reduce {
                NowPlayingMovieContract.State.Error(
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
            container.postEffect(NowPlayingMovieContract.Effect.OpenMovieDetail(Navigate { navigator ->
                (navigator as HomeNavigator).forwardToMovieDetailScreen(itemId)
            }))
        }
    }
}