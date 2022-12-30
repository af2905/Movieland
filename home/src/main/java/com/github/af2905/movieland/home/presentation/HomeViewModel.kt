package com.github.af2905.movieland.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.base.Container
import com.github.af2905.movieland.core.common.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.core.common.model.item.HomeMenuItem
import com.github.af2905.movieland.home.domain.params.*
import com.github.af2905.movieland.home.domain.usecase.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getNowPlayingMovies: GetNowPlayingMovies,
    private val getPopularMovies: GetPopularMovies,
    private val getUpcomingMovies: GetUpcomingMovies,
    private val getTopRatedMovies: GetTopRatedMovies,
    private val getPopularTvShows: GetPopularTvShows,
    private val getTopRatedTvShows: GetTopRatedTvShows,
    private val forceUpdate: ForceUpdate,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    val container: Container<HomeContract.State, HomeContract.Effect> =
        Container(viewModelScope, HomeContract.State.Init())

    init {
        viewModelScope.launch {
            uploadMoviesInit(this)
            initMenu()
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
        val nowPlayingMoviesAsync = scope.async {
            getNowPlayingMovies(NowPlayingMoviesParams()).getOrDefault(emptyList())
        }
        val popularTvShowsAsync = scope.async {
            getPopularTvShows(TvShowsParams()).getOrDefault(emptyList())
        }
        val topRatedTvShowsAsync = scope.async {
            getTopRatedTvShows(TvShowsParams()).getOrDefault(emptyList())
        }

        popularMoviesAsync.await()
        upcomingMoviesAsync.await()
        topRatedMoviesAsync.await()
        nowPlayingMoviesAsync.await()
        popularTvShowsAsync.await()
        topRatedTvShowsAsync.await()
    }

    private fun initMenu() = container.intent {
        container.reduce { HomeContract.State.Content(list = HomeMenuItem.getList()) }
    }

    fun setForceUpdate() = viewModelScope.launch(coroutineDispatcherProvider.main()) {
        forceUpdate.invoke(Unit)
    }
}