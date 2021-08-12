package com.github.af2905.movieland.presentation.feature.home

import com.github.af2905.movieland.domain.usecase.movies.GetNowPlayingMovies
import com.github.af2905.movieland.domain.usecase.movies.GetPopularMovies
import com.github.af2905.movieland.domain.usecase.params.PopularMoviesParams
import com.github.af2905.movieland.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.presentation.base.BaseViewModel
import com.github.af2905.movieland.presentation.model.Model
import com.github.af2905.movieland.presentation.model.item.LoadingItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies,
    private val getNowPlayingMovies: GetNowPlayingMovies,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : BaseViewModel<HomeNavigator>(coroutineDispatcherProvider) {

    private val _items = MutableStateFlow<List<Model>>(listOf())
    val items = _items.asStateFlow()

    private fun starter() = listOf(LoadingItem())

    init {
        _items.tryEmit(starter())

        launchIO(
            params = PopularMoviesParams(),
            execute = getPopularMovies::invoke
        ) {
            _items.tryEmit(it.movies)
        }
    }

    fun openDetail() = navigate { forwardMovieDetail() }

}