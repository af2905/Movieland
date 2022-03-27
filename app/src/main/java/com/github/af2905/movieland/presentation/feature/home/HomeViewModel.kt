package com.github.af2905.movieland.presentation.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.domain.usecase.movies.*
import com.github.af2905.movieland.domain.usecase.params.NowPlayingMoviesParams
import com.github.af2905.movieland.domain.usecase.params.PopularMoviesParams
import com.github.af2905.movieland.domain.usecase.params.TopRatedMoviesParams
import com.github.af2905.movieland.domain.usecase.params.UpcomingMoviesParams
import com.github.af2905.movieland.helper.coroutine.CoroutineDispatcherProvider
import com.github.af2905.movieland.presentation.base.Container
import com.github.af2905.movieland.presentation.common.ErrorHandler
import com.github.af2905.movieland.presentation.common.effect.Navigate
import com.github.af2905.movieland.presentation.common.effect.ToastMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getNowPlayingMovies: GetNowPlayingMovies,
    private val getPopularMovies: GetPopularMovies,
    private val getUpcomingMovies: GetUpcomingMovies,
    private val getTopRatedMovies: GetTopRatedMovies,
    private val forceUpdate: ForceUpdate,
    private val homeRepository: HomeRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    val container: Container<HomeContract.State, HomeContract.Effect> =
        Container(viewModelScope, HomeContract.State.Content(isLoading = true))

    val isLoading = container.state
        .filter { it is HomeContract.State.Content }
        .map { (it as HomeContract.State.Content).isLoading }
        .asLiveData()

    val items = container.state
        .filter { it is HomeContract.State.Content }
        .map { (it as HomeContract.State.Content).list }
        .asLiveData()

    val header = container.state
        .filter { it is HomeContract.State.Content }
        .map { (it as HomeContract.State.Content).header }
        .asLiveData()

    val headerVisible = container.state
        .filter { it is HomeContract.State.Content }
        .map { !((it as HomeContract.State.Content).list).isNullOrEmpty() }
        .asLiveData()

    init {
        viewModelScope.launch {
            homeRepository.subscribeOnForceUpdate(this) { force -> if (force) refresh() }
            loadData()
            uploadMoviesInit(this)
        }
    }

    private suspend fun uploadMoviesInit(scope: CoroutineScope) {
        val popularMoviesAsync = scope.async {
            getPopularMovies(PopularMoviesParams()).getOrDefault(emptyList())
        }
        val upcomingMoviesAsync = scope.async {
            getUpcomingMovies(UpcomingMoviesParams()).getOrDefault(emptyList())
        }
        val topRatedMoviesAsync = scope.async {
            getTopRatedMovies(TopRatedMoviesParams()).getOrDefault(emptyList())
        }
        popularMoviesAsync.await()
        upcomingMoviesAsync.await()
        topRatedMoviesAsync.await()
    }

    private fun loadData(forceUpdate: Boolean = false) {
        container.intent {
            try {
                getNowPlayingMovies(NowPlayingMoviesParams(forceUpdate = forceUpdate)).let { result ->
                    container.reduce {
                        result.getOrThrow().let {
                            HomeContract.State.Content(isLoading = false, list = it)
                        }
                    }
                }
            } catch (e: Exception) {
                container.reduce {
                    HomeContract.State.Content(isLoading = false, error = e)
                }
                container.postEffect(
                    HomeContract.Effect.ShowFailMessage(
                        ToastMessage(ErrorHandler.handleError(e))
                    )
                )
            }
        }
    }

    fun setForceUpdate() =
        viewModelScope.launch(coroutineDispatcherProvider.main()) { forceUpdate.invoke(Unit) }

    private fun refresh() = loadData(forceUpdate = true)

    fun openDetail(itemId: Int) = navigateToDetail(itemId)

    private fun navigateToDetail(itemId: Int) {
        container.intent {
            container.postEffect(HomeContract.Effect.OpenMovieDetail(Navigate { navigator ->
                (navigator as HomeNavigator).forwardMovieDetail(itemId)
            }))
        }
    }
}