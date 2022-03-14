package com.github.af2905.movieland.presentation.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.R
import com.github.af2905.movieland.domain.usecase.movies.*
import com.github.af2905.movieland.domain.usecase.params.NowPlayingMoviesParams
import com.github.af2905.movieland.domain.usecase.params.PopularMoviesParams
import com.github.af2905.movieland.domain.usecase.params.TopRatedMoviesParams
import com.github.af2905.movieland.domain.usecase.params.UpcomingMoviesParams
import com.github.af2905.movieland.helper.coroutine.CoroutineDispatcherProvider
import com.github.af2905.movieland.helper.text.UiText
import com.github.af2905.movieland.presentation.base.Container
import com.github.af2905.movieland.presentation.common.effect.Navigate
import com.github.af2905.movieland.presentation.common.effect.ToastMessage
import com.github.af2905.movieland.presentation.model.Model
import com.github.af2905.movieland.presentation.model.item.HeaderItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        Container(viewModelScope, HomeContract.State.Loading())

    var header = HeaderItem(R.string.now_playing)

    private val _headerVisible = MutableStateFlow(false)
    val headerVisible = _headerVisible

    private val _nowPlayingMovies = MutableStateFlow<List<Model>>(listOf())
    val nowPlayingMovies = _nowPlayingMovies.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    init {
        _loading.tryEmit(true)
        container.intent {
            this.scope.launch {
                homeRepository.subscribeOnForceUpdate(this) { force -> if (force) refresh() }
                loadData()
                uploadMoviesInit(this)
                homeRepository.subscribeOnAllMoviesUploaded(this) { uploaded ->
                    if (uploaded) allMoviesUploaded()
                }
            }
        }
    }

    private fun uploadMoviesInit(scope: CoroutineScope) {
        scope.launch {
            getPopularMovies(PopularMoviesParams())
                .getOrDefault(emptyList())
                .let { homeRepository.popularMoviesUploaded() }
        }
        scope.launch {
            getUpcomingMovies(UpcomingMoviesParams())
                .getOrDefault(emptyList())
                .let { homeRepository.upcomingMoviesUploaded() }
        }
        scope.launch {
            getTopRatedMovies(TopRatedMoviesParams())
                .getOrDefault(emptyList())
                .let { homeRepository.topRatedMoviesUploaded() }
        }
    }

    private fun loadData(forceUpdate: Boolean = false) {
        container.intent {
            container.reduce { HomeContract.State.Loading(nowPlayingMovies.value) }
            try {
                getNowPlayingMovies(NowPlayingMoviesParams(forceUpdate = forceUpdate)).let { result ->
                    container.reduce {
                        result.getOrThrow().let {
                            homeRepository.nowPlayingMoviesUploaded()
                            if (it.isEmpty()) HomeContract.State.EmptyResult
                            else HomeContract.State.Success(it)
                        }
                    }
                }
            } catch (e: Exception) {
                container.reduce { HomeContract.State.Error(e) }
            }
        }
    }

    fun updateData(movies: List<Model>, headerVisibility: Boolean) {
        _nowPlayingMovies.value = movies
        _headerVisible.tryEmit(headerVisibility)
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

    fun showError(error: UiText) {
        container.intent {
            container.postEffect(HomeContract.Effect.ShowFailMessage(ToastMessage(error)))
        }
    }

    private fun allMoviesUploaded() = _loading.tryEmit(false)
}