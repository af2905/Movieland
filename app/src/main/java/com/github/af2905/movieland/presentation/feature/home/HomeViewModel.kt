package com.github.af2905.movieland.presentation.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.af2905.movieland.R
import com.github.af2905.movieland.domain.usecase.adult.GetAdultMovies
import com.github.af2905.movieland.domain.usecase.movies.*
import com.github.af2905.movieland.domain.usecase.params.NowPlayingMoviesParams
import com.github.af2905.movieland.domain.usecase.params.PopularMoviesParams
import com.github.af2905.movieland.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.presentation.base.BaseViewModel
import com.github.af2905.movieland.presentation.model.ItemIds.HORIZONTAL_ITEM_LIST_ID
import com.github.af2905.movieland.presentation.model.Model
import com.github.af2905.movieland.presentation.model.item.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
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

    val header = HeaderItem(R.string.now_playing)
    val headerTop3 = HeaderItem(R.string.top_3_rated)

    private val _nowPlayingMovies = MutableLiveData<List<Model>>()
    val nowPlayingMovies: LiveData<List<Model>> = _nowPlayingMovies

    private val _top3Movies = MutableLiveData<List<Model>>()
    val top3Movies: LiveData<List<Model>> = _top3Movies

    init {
        loadData()
    }

    private fun loadData(forced: Boolean = false) {
        launchUI {
            loading.emit(true)
            val nowPlayingList = mutableListOf<Model>()
            val top3List = mutableListOf<Model>()

            val nowPlaying = loadNowPlayingMoviesAsync(this, forced)
            val top3 = loadTop3Async(this, forced)

            nowPlayingList.addAll(nowPlaying.await().getOrDefault(emptyList()))
            top3List.addAll(top3.await().getOrDefault(emptyList()))

            //val header = listOf(HeaderItem(R.string.header), emptySpaceMedium)


            /*val popular = loadPopularMoviesAsync(this, forced)
            val topRated = loadTopRatedMoviesAsync(this, forced)
            val upcoming = loadUpcomingMoviesAsync(this, forced)



            list.addAll(header)*/


            /*list.addAll(top3.await().getOrDefault(emptyList()))

            list.addAll(popular.await().getOrDefault(emptyList()))
            list.addAll(topRated.await().getOrDefault(emptyList()))
            list.addAll(upcoming.await().getOrDefault(emptyList()))*/
            //list.addAll(listOf(emptySpaceBig))
            _nowPlayingMovies.value = nowPlayingList
            _top3Movies.value = top3List
            loading.emit(false)
        }
    }

    private suspend fun loadNowPlayingMoviesAsync(coroutineScope: CoroutineScope, forced: Boolean)
            : Deferred<Result<List<Model>>> {
        val deferredNowPlaying = coroutineScope.iOAsync {
            return@iOAsync getNowPlayingMovies(NowPlayingMoviesParams(forced = forced))
                .getOrThrow().movies ?: emptyList()
        }
        return deferredNowPlaying
    }

    private suspend fun loadTop3Async(coroutineScope: CoroutineScope, forced: Boolean)
            : Deferred<Result<List<Model>>> {
        val deferredTop3 = coroutineScope.iOAsync {
            val top3 = getTop3Movies { getPopularMovies(PopularMoviesParams(forced = forced)) }
                .getOrThrow().movies?.map {
                    MovieItemVariant(it)
                } ?: emptyList()
            if (top3.isNotEmpty()) {
                mutableListOf<Model>().apply {
                    add(DividerItem())
                    top3.map { addAll(listOf(it, DividerItem())) }
                }
            } else emptyList()
        }
        return deferredTop3
    }

    /* private suspend fun loadPopularMoviesAsync(
         coroutineScope: CoroutineScope,
         forced: Boolean
     ): Deferred<Result<List<Model>>> {
         val deferredPopular = coroutineScope.iOAsync {
             val popularMovies =
                 getPopularMovies(PopularMoviesParams(forced = forced)).getOrThrow().movies
                     ?: listOf()
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

     private suspend fun loadTopRatedMoviesAsync(
         coroutineScope: CoroutineScope,
         forced: Boolean
     ): Deferred<Result<List<Model>>> {
         val deferredTopRated = coroutineScope.iOAsync {
             val topRatedMovies =
                 getTopRatedMovies(TopRatedMoviesParams(forced = forced)).getOrThrow().movies
                     ?: listOf()
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

     private suspend fun loadUpcomingMoviesAsync(
         coroutineScope: CoroutineScope,
         forced: Boolean
     ): Deferred<Result<List<Model>>> {
         val deferredUpcoming = coroutineScope.iOAsync {
             val upcomingMovies =
                 getUpcomingMovies(UpcomingMoviesParams(forced = forced)).getOrThrow().movies
                     ?: listOf()
             if (!upcomingMovies.isNullOrEmpty()) {
                 listOf(
                     HeaderItem(R.string.upcoming),
                     emptySpaceMedium,
                     HorizontalListItem(upcomingMovies, id = UPCOMING_HORIZONTAL_LIST_ITEM_ID),
                     emptySpaceMedium
                 )
             } else emptyList()
         }
         return deferredUpcoming
     }*/

    fun openDetail(itemId: Int, position: Int) = navigator { forwardMovieDetail(itemId) }
    fun refresh() = loadData(forced = true)

    companion object {
        const val UPCOMING_HORIZONTAL_LIST_ITEM_ID = HORIZONTAL_ITEM_LIST_ID * 10000 + 1
        const val TOP_RATED_HORIZONTAL_LIST_ITEM_ID = HORIZONTAL_ITEM_LIST_ID * 10000 + 2
        const val POPULAR_HORIZONTAL_LIST_ITEM_ID = HORIZONTAL_ITEM_LIST_ID * 10000 + 3
        const val NOW_PLAYING_HORIZONTAL_LIST_ITEM_ID = HORIZONTAL_ITEM_LIST_ID * 10000 + 4
    }
}