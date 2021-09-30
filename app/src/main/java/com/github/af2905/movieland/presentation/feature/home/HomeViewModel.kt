package com.github.af2905.movieland.presentation.feature.home

import androidx.lifecycle.LiveData
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
import com.github.af2905.movieland.presentation.model.ItemIds.HORIZONTAL_ITEM_LIST_ID
import com.github.af2905.movieland.presentation.model.Model
import com.github.af2905.movieland.presentation.model.item.EmptySpaceItem
import com.github.af2905.movieland.presentation.model.item.HeaderItem
import com.github.af2905.movieland.presentation.model.item.HorizontalListItem
import com.github.af2905.movieland.presentation.model.item.MovieItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
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
    val items: LiveData<List<Model>> = _items

    private val _header = MutableLiveData<List<Model>>()

/*    private val listItems = CopyOnWriteArrayList<Model>()
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
    }*/

    init {
        loadData()
    }

    private fun loadData() {
        launchUI {
            loading.emit(true)
            val list = mutableListOf<Model>()
            val header = listOf(HeaderItem(R.string.header), emptySpaceMedium)

            val nowPlaying = loadNowPlayingMoviesAsync(this)
            val popular = loadPopularMoviesAsync(this)
            val topRated = loadTopRatedMoviesAsync(this)
            val upcoming = loadUpcomingMoviesAsync(this)

            list.addAll(header)
            list.addAll(nowPlaying.await().getOrDefault(emptyList()))
            list.addAll(popular.await().getOrDefault(emptyList()))
            list.addAll(topRated.await().getOrDefault(emptyList()))
            list.addAll(upcoming.await().getOrDefault(emptyList()))
            list.addAll(listOf(emptySpaceBig))
            _items.value = list
            loading.emit(false)
        }
    }

    private suspend fun loadNowPlayingMoviesAsync(coroutineScope: CoroutineScope): Deferred<Result<List<Model>>> {
        val deferredNowPlaying = coroutineScope.iOAsync {
            val nowPlaying =
                getNowPlayingMovies(NowPlayingMoviesParams()).extractData?.movies ?: listOf()
            if (nowPlaying.isNotEmpty()) {
                listOf(
                    HeaderItem(R.string.now_playing), emptySpaceMedium,
                    HorizontalListItem(nowPlaying, id = HORIZONTAL_ITEM_LIST_ID * 1000 + 1), emptySpaceMedium
                )
            } else emptyList()
        }
        return deferredNowPlaying
    }

    private suspend fun loadPopularMoviesAsync(coroutineScope: CoroutineScope): Deferred<Result<List<Model>>> {
        val deferredPopular = coroutineScope.iOAsync {
            val popularMovies =
                getPopularMovies(PopularMoviesParams()).extractData?.movies ?: listOf()
            if (popularMovies.isNotEmpty()) {
                listOf(
                    HeaderItem(R.string.popular), emptySpaceMedium,
                    HorizontalListItem(popularMovies, id = HORIZONTAL_ITEM_LIST_ID * 1000 + 2), emptySpaceMedium
                )
            } else emptyList()
        }
        return deferredPopular
    }

    private suspend fun loadTopRatedMoviesAsync(coroutineScope: CoroutineScope): Deferred<Result<List<Model>>> {
        val deferredTopRated = coroutineScope.iOAsync {
            val topRatedMovies =
                getTopRatedMovies(TopRatedMoviesParams()).extractData?.movies ?: listOf()
            if (topRatedMovies.isNotEmpty()) {
                listOf(
                    HeaderItem(R.string.top_rated), emptySpaceMedium,
                    HorizontalListItem(topRatedMovies, id = HORIZONTAL_ITEM_LIST_ID * 1000 + 3), emptySpaceMedium

                )
            } else emptyList()
        }
        return deferredTopRated
    }

    private suspend fun loadUpcomingMoviesAsync(coroutineScope: CoroutineScope): Deferred<Result<List<Model>>> {
        val deferredUpcoming = coroutineScope.iOAsync {
            val upcomingMovies =
                getUpcomingMovies(UpcomingMoviesParams()).extractData?.movies
                    ?: listOf()
            if (upcomingMovies.isNotEmpty()) {
                listOf(
                    HeaderItem(R.string.upcoming), emptySpaceMedium,
                    HorizontalListItem(upcomingMovies, id = HORIZONTAL_ITEM_LIST_ID * 1000 + 4), emptySpaceMedium
                )
            } else emptyList()
        }
        return deferredUpcoming
    }

/*    private fun loadData() {
        launchUI {
            loading.emit(true)
            listOf(
                loadHeaderAsync(this),
                loadNowPlayingMoviesAsync(this),
                loadPopularMoviesAsync(this),
                loadTopRatedMoviesAsync(this),
                loadUpcomingMoviesAsync(this)
            ).awaitAll()
            loading.emit(false)
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
    }*/

    fun openDetail(item: MovieItem, position: Int) = navigate { forwardMovieDetail(item.id) }
    fun refresh() {
        //listItems.clear()
        //_items.value = emptyList()
        loadData()
    }

}