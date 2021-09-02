package com.github.af2905.movieland.presentation.feature.home

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.awaitAll
import java.util.concurrent.CopyOnWriteArrayList
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies,
    private val getNowPlayingMovies: GetNowPlayingMovies,
    private val getTopRatedMovies: GetTopRatedMovies,
    private val getUpcomingMovies: GetUpcomingMovies,
    private val getAdultMovies: GetAdultMovies,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : BaseViewModel<HomeNavigator>(coroutineDispatcherProvider) {

    private val emptySpaceSmall = EmptySpaceItem(R.dimen.default_margin_small)
    private val emptySpaceNormal = EmptySpaceItem(R.dimen.default_margin)
    private val emptySpaceMedium = EmptySpaceItem(R.dimen.default_margin_medium)
    private val emptySpaceBig = EmptySpaceItem(R.dimen.default_margin_big)

    private val _items = MutableLiveData<List<Model>>()
    private val _header = MutableLiveData<List<Model>>()

    private val listItems = CopyOnWriteArrayList<Model>()

    val items = MediatorLiveData<List<Model>>().apply {
        addSource(_items) {
            val headerValue = _header.value
            if (headerValue?.isNotEmpty() == true) {
                value = headerValue + it + emptySpaceBig
            }
        }
        addSource(_header) {
            value = it + (_items.value ?: emptyList()) + emptySpaceBig
        }
    }

    private fun starter() = listOf(LoadingItem())

    init {
        _items.postValue(starter())
        loadData()
    }

    private fun loadData() {
        launchUI {
            listOf(
                loadHeaderAsync(this),
                loadNowPlayingMoviesAsync(this),
                loadPopularMoviesAsync(this),
                loadTopRatedMoviesAsync(this),
                loadUpcomingMoviesAsync(this)
            ).awaitAll()
        }
    }

    private suspend fun loadHeaderAsync(coroutineScope: CoroutineScope): Deferred<Result<Unit>> {
        return coroutineScope.iOAsync {
            val header =
                listOf(HeaderItem(R.string.header), emptySpaceMedium)
            _header.postValue(header)
        }
    }

    private suspend fun loadNowPlayingMoviesAsync(coroutineScope: CoroutineScope): Deferred<Result<Unit>> {
        return coroutineScope.iOAsync {
            val nowPlaying =
                getNowPlayingMovies(NowPlayingMoviesParams()).extractData?.movies ?: listOf()
            val movies = if (nowPlaying.isNotEmpty()) {
                listOf(
                    HeaderItem(R.string.now_playing), emptySpaceMedium,
                    HorizontalListItem(nowPlaying), emptySpaceMedium
                )
            } else {
                emptyList()
            }
            listItems.addAll(movies)
            _items.postValue(listItems)
        }
    }

    private suspend fun loadPopularMoviesAsync(coroutineScope: CoroutineScope): Deferred<Result<Unit>> {
        return coroutineScope.iOAsync {
            val popularMovies =
                getPopularMovies(PopularMoviesParams()).extractData?.movies
                    ?: listOf()
            val movies = if (popularMovies.isNotEmpty()) {
                listOf(
                    HeaderItem(R.string.popular), emptySpaceMedium,
                    HorizontalListItem(popularMovies), emptySpaceMedium

                )
            } else {
                emptyList()
            }
            listItems.addAll(movies)
            _items.postValue(listItems)
        }
    }

    private suspend fun loadTopRatedMoviesAsync(coroutineScope: CoroutineScope): Deferred<Result<Unit>> {
        return coroutineScope.iOAsync {
            val topRatedMovies =
                getTopRatedMovies(TopRatedMoviesParams()).extractData?.movies
                    ?: listOf()
            val movies = if (topRatedMovies.isNotEmpty()) {
                listOf(
                    HeaderItem(R.string.top_rated), emptySpaceMedium,
                    HorizontalListItem(topRatedMovies), emptySpaceMedium

                )
            } else {
                emptyList()
            }
            listItems.addAll(movies)
            _items.postValue(listItems)
        }
    }

    private suspend fun loadUpcomingMoviesAsync(coroutineScope: CoroutineScope): Deferred<Result<Unit>> {
        return coroutineScope.iOAsync {

            val upcomingMovies =
                getUpcomingMovies(UpcomingMoviesParams()).extractData?.movies
                    ?: listOf()
            val movies = if (upcomingMovies.isNotEmpty()) {
                listOf(
                    HeaderItem(R.string.upcoming), emptySpaceMedium,
                    HorizontalListItem(upcomingMovies), emptySpaceMedium
                )
            } else {
                emptyList()
            }
            listItems.addAll(movies)
            _items.postValue(listItems)
        }
    }


    fun openDetail(item: MovieItem, position: Int) = navigate { forwardMovieDetail(item.id) }

}