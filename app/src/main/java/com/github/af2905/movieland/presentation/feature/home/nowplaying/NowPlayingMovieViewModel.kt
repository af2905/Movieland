package com.github.af2905.movieland.presentation.feature.home.nowplaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.domain.usecase.movies.GetNowPlayingMovies
import com.github.af2905.movieland.domain.usecase.params.NowPlayingMoviesParams
import com.github.af2905.movieland.helper.coroutine.CoroutineDispatcherProvider
import com.github.af2905.movieland.presentation.base.Container
import com.github.af2905.movieland.presentation.common.effect.Navigate
import com.github.af2905.movieland.presentation.feature.home.HomeNavigator
import com.github.af2905.movieland.presentation.feature.home.HomeRepository
import com.github.af2905.movieland.presentation.model.item.ErrorItem
import com.github.af2905.movieland.presentation.model.item.MovieItemVariant
import kotlinx.coroutines.launch
import javax.inject.Inject

class NowPlayingMovieViewModel @Inject constructor(
    private val getNowPlayingMovies: GetNowPlayingMovies,
    private val homeRepository: HomeRepository,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    val container: Container<NowPlayingMovieContract.State, NowPlayingMovieContract.Effect> =
        Container(viewModelScope, NowPlayingMovieContract.State.Init())

    init {
        loadData()
        viewModelScope.launch(coroutineDispatcherProvider.main()) {
            homeRepository.subscribeOnForceUpdate(this) { force -> if (force) refresh() }
        }
    }

    private fun loadData(forceUpdate: Boolean = false) {
        container.intent {
            container.reduce {
                NowPlayingMovieContract.State.Init(list = this.list)
            }
            val result = getNowPlayingMovies(NowPlayingMoviesParams(forceUpdate = forceUpdate))
            if (result.isFailure) {
                val error = result.exceptionOrNull()
                container.reduce {
                    NowPlayingMovieContract.State.Error(
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
                    NowPlayingMovieContract.State.Content(
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
            container.postEffect(NowPlayingMovieContract.Effect.OpenMovieDetail(Navigate { navigator ->
                (navigator as HomeNavigator).forwardMovieDetail(itemId)
            }))
        }
    }
}