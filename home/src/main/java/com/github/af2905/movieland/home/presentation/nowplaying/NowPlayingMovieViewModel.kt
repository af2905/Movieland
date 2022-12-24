package com.github.af2905.movieland.home.presentation.nowplaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.base.Container
import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.core.common.model.item.ErrorItem
import com.github.af2905.movieland.core.common.model.item.MovieItemV2
import com.github.af2905.movieland.home.HomeRepository
import com.github.af2905.movieland.home.domain.params.NowPlayingMoviesParams
import com.github.af2905.movieland.home.domain.usecase.GetNowPlayingMovies
import com.github.af2905.movieland.home.presentation.HomeNavigator
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
                        list = movies.map { MovieItemV2(it) }
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