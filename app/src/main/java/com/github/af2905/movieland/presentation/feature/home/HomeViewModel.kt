package com.github.af2905.movieland.presentation.feature.home

import com.github.af2905.movieland.domain.usecase.adult.GetAdultMovies
import com.github.af2905.movieland.domain.usecase.movies.GetNowPlayingMovies
import com.github.af2905.movieland.domain.usecase.movies.GetPopularMovies
import com.github.af2905.movieland.domain.usecase.movies.GetTopRatedMovies
import com.github.af2905.movieland.domain.usecase.movies.GetUpcomingMovies
import com.github.af2905.movieland.domain.usecase.params.NowPlayingMoviesParams
import com.github.af2905.movieland.domain.usecase.params.PopularMoviesParams
import com.github.af2905.movieland.domain.usecase.params.TopRatedMoviesParams
import com.github.af2905.movieland.domain.usecase.params.UpcomingMoviesParams
import com.github.af2905.movieland.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.presentation.base.BaseViewModel
import com.github.af2905.movieland.presentation.model.Model
import com.github.af2905.movieland.presentation.model.item.MovieItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies,
    private val getNowPlayingMovies: GetNowPlayingMovies,
    private val getTopRatedMovies: GetTopRatedMovies,
    private val getUpcomingMovies: GetUpcomingMovies,
    private val getAdultMovies: GetAdultMovies,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : BaseViewModel<HomeNavigator>(coroutineDispatcherProvider) {

    private val _items = MutableStateFlow<List<Model>>(emptyList())
    val items: StateFlow<List<Model>> = _items

    private val _nowPlayingMovies = MutableStateFlow<List<Model>>(emptyList())
    val nowPlayingMovies: StateFlow<List<Model>> = _nowPlayingMovies

    private val _popularMovies = MutableStateFlow<List<Model>>(emptyList())
    val popularMovies: StateFlow<List<Model>> = _popularMovies

    private val _topRatedMovies = MutableStateFlow<List<Model>>(emptyList())
    val topRatedMovies: StateFlow<List<Model>> = _topRatedMovies

    private val _upcomingMovies = MutableStateFlow<List<Model>>(emptyList())
    val upcomingMovies: StateFlow<List<Model>> = _upcomingMovies

    private val _nowPlayingTitleVisible = MutableStateFlow(false)
    val nowPlayingTitleVisible: StateFlow<Boolean> = _nowPlayingTitleVisible

    private val _popularTitleVisible = MutableStateFlow(false)
    val popularTitleVisible: StateFlow<Boolean> = _popularTitleVisible

    private val _topRatedTitleVisible = MutableStateFlow(false)
    val topRatedTitleVisible: StateFlow<Boolean> = _topRatedTitleVisible

    private val _upcomingTitleVisible = MutableStateFlow(false)
    val upcomingTitleVisible: StateFlow<Boolean> = _upcomingTitleVisible

    init {
        loadData()
    }

    private fun loadData() {
        launchUI {
            loading.emit(true)

            val nowPlayingAsync = loadNowPlayingMoviesAsync(this)
            val popularAsync = loadPopularMoviesAsync(this)
            val topRatedAsync = loadTopRatedMoviesAsync(this)
            val upcomingAsync = loadUpcomingMoviesAsync(this)

            val nowPlaying = nowPlayingAsync.await().getOrDefault(emptyList())
            val popular = popularAsync.await().getOrDefault(emptyList())
            val topRated = topRatedAsync.await().getOrDefault(emptyList())
            val upcoming = upcomingAsync.await().getOrDefault(emptyList())

            _nowPlayingMovies.emit(nowPlaying)
            _popularMovies.emit(popular)
            _topRatedMovies.emit(topRated)
            _upcomingMovies.emit(upcoming)

            loading.emit(false)

            _nowPlayingTitleVisible.emit(nowPlaying.isNotEmpty())
            _popularTitleVisible.emit(popular.isNotEmpty())
            _topRatedTitleVisible.emit(topRated.isNotEmpty())
            _upcomingTitleVisible.emit(upcoming.isNotEmpty())
        }
    }

    private suspend fun loadNowPlayingMoviesAsync(coroutineScope: CoroutineScope): Deferred<Result<List<Model>>> {
        return coroutineScope.iOAsync {
            getNowPlayingMovies(NowPlayingMoviesParams()).extractData?.movies ?: emptyList()
        }
    }

    private suspend fun loadPopularMoviesAsync(coroutineScope: CoroutineScope): Deferred<Result<List<Model>>> {
        return coroutineScope.iOAsync {
            getPopularMovies(PopularMoviesParams()).extractData?.movies ?: emptyList()
        }
    }

    private suspend fun loadTopRatedMoviesAsync(coroutineScope: CoroutineScope): Deferred<Result<List<Model>>> {
        return coroutineScope.iOAsync {
            getTopRatedMovies(TopRatedMoviesParams()).extractData?.movies ?: emptyList()
        }
    }

    private suspend fun loadUpcomingMoviesAsync(coroutineScope: CoroutineScope): Deferred<Result<List<Model>>> {
        return coroutineScope.iOAsync {
            getUpcomingMovies(UpcomingMoviesParams()).extractData?.movies ?: emptyList()
        }
    }

    fun openDetail(item: MovieItem, position: Int) = navigate { forwardMovieDetail(item.id) }
    fun refresh() = loadData()

}