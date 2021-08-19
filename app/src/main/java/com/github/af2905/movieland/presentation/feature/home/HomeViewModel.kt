package com.github.af2905.movieland.presentation.feature.home

import com.github.af2905.movieland.R
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
import com.github.af2905.movieland.presentation.model.item.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies,
    private val getNowPlayingMovies: GetNowPlayingMovies,
    private val getTopRatedMovies: GetTopRatedMovies,
    private val getUpcomingMovies: GetUpcomingMovies,
    private val getAdultMovies: GetAdultMovies,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : BaseViewModel<HomeNavigator>(coroutineDispatcherProvider) {

    private val _items = MutableStateFlow<List<Model>>(listOf())
    val items = _items.asStateFlow()

    private val emptySpaceSmall = EmptySpaceItem(R.dimen.default_margin_small)
    private val emptySpaceNormal = EmptySpaceItem(R.dimen.default_margin)
    private val emptySpaceMedium = EmptySpaceItem(R.dimen.default_margin_medium)
    private val emptySpaceBig = EmptySpaceItem(R.dimen.default_margin_big)

    private fun starter() = listOf(LoadingItem())

    init {
        loadData()
    }

    private fun loadData() {
        launchUI {
            val list = mutableListOf<Model>()
            list.addAll(loadNowPlayingMovies(this))
            list.addAll(loadPopularMovies(this))
            list.addAll(loadTopRatedMovies(this))
            list.addAll(loadUpcomingMovies(this))
            list.addAll(listOf(emptySpaceBig, emptySpaceBig))
            _items.emit(list)
        }
    }

    private suspend fun loadNowPlayingMovies(coroutineScope: CoroutineScope): List<Model> {
        val deferredNowPlaying = coroutineScope.async {
            try {
                val nowPlaying =
                    getNowPlayingMovies(NowPlayingMoviesParams()).extractData?.movies
                        ?: listOf()
                if (nowPlaying.isNotEmpty()) {
                    listOf(
                        HeaderItem(R.string.now_playing), emptySpaceMedium,
                        HorizontalListItem(nowPlaying), emptySpaceMedium
                    )
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                emptyList()
            }
        }
        return deferredNowPlaying.await()
    }

    private suspend fun loadPopularMovies(coroutineScope: CoroutineScope): List<Model> {
        val deferredPopular = coroutineScope.async {
            try {
                val popularMovies =
                    getPopularMovies(PopularMoviesParams()).extractData?.movies
                        ?: listOf()
                if (popularMovies.isNotEmpty()) {
                    listOf(
                        HeaderItem(R.string.popular), emptySpaceMedium,
                        HorizontalListItem(popularMovies), emptySpaceMedium

                    )
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                emptyList()
            }
        }
        return deferredPopular.await()
    }

    private suspend fun loadTopRatedMovies(coroutineScope: CoroutineScope): List<Model> {
        val deferredTopRated = coroutineScope.async {
            try {
                val topRatedMovies =
                    getTopRatedMovies(TopRatedMoviesParams()).extractData?.movies
                        ?: listOf()
                if (topRatedMovies.isNotEmpty()) {
                    listOf(
                        HeaderItem(R.string.top_rated), emptySpaceMedium,
                        HorizontalListItem(topRatedMovies), emptySpaceMedium

                    )
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                emptyList()
            }
        }
        return deferredTopRated.await()
    }

    private suspend fun loadUpcomingMovies(coroutineScope: CoroutineScope): List<Model> {
        val deferredUpcoming = coroutineScope.async {
            try {
                val upcomingMovies =
                    getUpcomingMovies(UpcomingMoviesParams()).extractData?.movies
                        ?: listOf()
                if (upcomingMovies.isNotEmpty()) {
                    listOf(
                        HeaderItem(R.string.upcoming), emptySpaceMedium,
                        HorizontalListItem(upcomingMovies), emptySpaceMedium
                    )
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                emptyList()
            }
        }
        return deferredUpcoming.await()
    }

    fun openDetail(item: MovieItem, position: Int) = navigate { forwardMovieDetail() }

}