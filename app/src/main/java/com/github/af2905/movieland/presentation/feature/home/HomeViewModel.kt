package com.github.af2905.movieland.presentation.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.af2905.movieland.R
import com.github.af2905.movieland.domain.usecase.adult.GetAdultMovies
import com.github.af2905.movieland.domain.usecase.movies.*
import com.github.af2905.movieland.domain.usecase.params.NowPlayingMoviesParams
import com.github.af2905.movieland.domain.usecase.params.PopularMoviesParams
import com.github.af2905.movieland.domain.usecase.params.TopRatedMoviesParams
import com.github.af2905.movieland.domain.usecase.params.UpcomingMoviesParams
import com.github.af2905.movieland.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.presentation.base.BaseViewModel
import com.github.af2905.movieland.presentation.model.ItemIds.HORIZONTAL_ITEM_LIST_ID
import com.github.af2905.movieland.presentation.model.Model
import com.github.af2905.movieland.presentation.model.item.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies,
    private val getNowPlayingMovies: GetNowPlayingMovies,
    private val getTopRatedMovies: GetTopRatedMovies,
    private val getUpcomingMovies: GetUpcomingMovies,
    private val getAdultMovies: GetAdultMovies,
    private val getTop3Movies: GetTop3Movies,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : BaseViewModel<HomeNavigator>(coroutineDispatcherProvider) {

    private val emptySpaceSmall = EmptySpaceItem(R.dimen.default_margin_small)
    private val emptySpaceNormal = EmptySpaceItem(R.dimen.default_margin)
    private val emptySpaceMedium = EmptySpaceItem(R.dimen.default_margin_medium)
    private val emptySpaceBig = EmptySpaceItem(R.dimen.default_margin_big)

    private val _items = MutableLiveData<List<Model>>()
    val items: LiveData<List<Model>> = _items

    private val _header = MutableLiveData<List<Model>>()

    private var horizontalItemListId: AtomicInteger = AtomicInteger(HORIZONTAL_ITEM_LIST_ID * 10000)

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
        horizontalItemListId.getAndIncrement()
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

            val top3 = loadTop3Async(this)

            list.addAll(header)
            list.addAll(nowPlaying.await().getOrDefault(emptyList()))

            list.addAll(top3.await().getOrDefault(emptyList()))

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
                    HeaderItem(R.string.now_playing),
                    emptySpaceMedium,
                    HorizontalListItem(nowPlaying, id = NOW_PLAYING_HORIZONTAL_LIST_ITEM_ID),
                    emptySpaceMedium
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
                    HeaderItem(R.string.popular),
                    emptySpaceMedium,
                    HorizontalListItem(popularMovies, id = POPULAR_HORIZONTAL_LIST_ITEM_ID),
                    emptySpaceMedium
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
                    HeaderItem(R.string.top_rated),
                    emptySpaceMedium,
                    HorizontalListItem(topRatedMovies, id = TOP_RATED_HORIZONTAL_LIST_ITEM_ID),
                    emptySpaceMedium
                )
            } else emptyList()
        }
        return deferredTopRated
    }

    private suspend fun loadUpcomingMoviesAsync(coroutineScope: CoroutineScope): Deferred<Result<List<Model>>> {
        val deferredUpcoming = coroutineScope.iOAsync {
            val upcomingMovies =
                getUpcomingMovies(UpcomingMoviesParams()).extractData?.movies ?: listOf()
            if (upcomingMovies.isNotEmpty()) {
                listOf(
                    HeaderItem(R.string.upcoming),
                    emptySpaceMedium,
                    HorizontalListItem(upcomingMovies, id = UPCOMING_HORIZONTAL_LIST_ITEM_ID),
                    emptySpaceMedium
                )
            } else emptyList()
        }
        return deferredUpcoming
    }

    private suspend fun loadTop3Async(coroutineScope: CoroutineScope): Deferred<Result<List<Model>>> {
        val deferredTop3 = coroutineScope.iOAsync {
            val top3Movies =
                getPopularMovies(PopularMoviesParams()).extractData?.movies
                    ?.filterNot { it.voteAverage == null }
                    ?.filter { it.voteAverage!! > 7.0 }
                    ?.take(3) ?: listOf()
            if (top3Movies.isNotEmpty() && top3Movies.size == 3) {

                mutableListOf<Model>().apply {
                    add(HeaderItem(R.string.top_3_rated))
                    addAll(listOf(emptySpaceMedium, DividerItem()))
                    top3Movies.map { addAll(listOf(MovieItemVariant(it), DividerItem())) }
                    add(emptySpaceMedium)
                }
            } else emptyList()
        }
        return deferredTop3
    }

    fun openDetail(itemId: Int, position: Int) = navigate { forwardMovieDetail(itemId) }
    fun refresh() = loadData()

    companion object {
        const val UPCOMING_HORIZONTAL_LIST_ITEM_ID = HORIZONTAL_ITEM_LIST_ID * 10000 + 1
        const val TOP_RATED_HORIZONTAL_LIST_ITEM_ID = HORIZONTAL_ITEM_LIST_ID * 10000 + 2
        const val POPULAR_HORIZONTAL_LIST_ITEM_ID = HORIZONTAL_ITEM_LIST_ID * 10000 + 3
        const val NOW_PLAYING_HORIZONTAL_LIST_ITEM_ID = HORIZONTAL_ITEM_LIST_ID * 10000 + 4
    }
}