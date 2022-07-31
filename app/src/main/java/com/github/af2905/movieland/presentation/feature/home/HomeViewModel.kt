package com.github.af2905.movieland.presentation.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.base.Container
import com.github.af2905.movieland.core.common.model.item.HomeMenuItem
import com.github.af2905.movieland.domain.usecase.movies.*
import com.github.af2905.movieland.domain.usecase.params.NowPlayingMoviesParams
import com.github.af2905.movieland.domain.usecase.params.PopularMoviesParams
import com.github.af2905.movieland.domain.usecase.params.TopRatedMoviesParams
import com.github.af2905.movieland.domain.usecase.params.UpcomingMoviesParams
import com.github.af2905.movieland.helper.coroutine.CoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getNowPlayingMovies: GetNowPlayingMovies,
    private val getPopularMovies: GetPopularMovies,
    private val getUpcomingMovies: GetUpcomingMovies,
    private val getTopRatedMovies: GetTopRatedMovies,
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
        popularMoviesAsync.await()
        upcomingMoviesAsync.await()
        topRatedMoviesAsync.await()
        nowPlayingMoviesAsync.await()
    }

    private fun initMenu() = container.intent {
        container.reduce { HomeContract.State.Content(list = HomeMenuItem.getList()) }
    }

    fun setForceUpdate() = viewModelScope.launch(coroutineDispatcherProvider.main()) {
        forceUpdate.invoke(Unit)
    }
}