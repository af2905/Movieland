package com.github.af2905.movieland.home.presentation

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.base.Container
import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.model.ItemIds
import com.github.af2905.movieland.core.common.model.Model
import com.github.af2905.movieland.core.common.model.item.*
import com.github.af2905.movieland.core.data.database.entity.MovieType
import com.github.af2905.movieland.core.data.database.entity.TvShowType
import com.github.af2905.movieland.core.shared.*
import com.github.af2905.movieland.detail.R
import com.github.af2905.movieland.home.presentation.item.PagerMovieItem
import com.github.af2905.movieland.home.presentation.item.PopularPersonItem
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.github.af2905.movieland.home.R as HomeResources

private const val PEOPLE_LIST_ID = ItemIds.HORIZONTAL_ITEM_LIST_ID * 1000 + 1
private const val MOVIES_LIST_ID = ItemIds.HORIZONTAL_ITEM_LIST_ID * 1000 + 2
private const val TV_SHOWS_LIST_ID = ItemIds.HORIZONTAL_ITEM_LIST_ID * 1000 + 3

class HomeViewModel @Inject constructor(
    private val getNowPlayingMovies: GetNowPlayingMovies,
    private val getPopularMovies: GetPopularMovies,
    private val getUpcomingMovies: GetUpcomingMovies,
    private val getTopRatedMovies: GetTopRatedMovies,
    private val getPopularTvShows: GetPopularTvShows,
    private val getTopRatedTvShows: GetTopRatedTvShows,
    private val getPopularPeople: GetPopularPeople,
    private val getCachedPopularPeople: GetCachedPopularPeople,
    private val getCachedMoviesByType: GetCachedMoviesByType,
    private val getCachedTvShowsByType: GetCachedTvShowsByType
) : ViewModel() {

    val container: Container<HomeContract.State, HomeContract.Effect> =
        Container(viewModelScope, HomeContract.State.Loading())

    private val emptySpaceSmall = EmptySpaceItem(R.dimen.default_margin_small)
    private val emptySpaceNormal = EmptySpaceItem(R.dimen.default_margin)

    init {
        savedLoadData()
    }

    private fun savedLoadData(forceUpdate: Boolean = false) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable -> handleError(throwable) }
        viewModelScope.launch(exceptionHandler) {
            loadData(scope = this, forceUpdate = forceUpdate)
        }
    }

    private suspend fun loadData(scope: CoroutineScope, forceUpdate: Boolean) {

        container.intent {
            container.reduce {
                HomeContract.State.Loading()
            }
        }

        val popularMoviesAsync = scope.async {
            getPopularMovies(PopularMoviesParams(forceUpdate = forceUpdate)).getOrThrow()
        }
        val upcomingMoviesAsync = scope.async {
            getUpcomingMovies(UpcomingMoviesParams(forceUpdate = forceUpdate)).getOrThrow()
        }
        val topRatedMoviesAsync = scope.async {
            getTopRatedMovies(TopRatedMoviesParams(forceUpdate = forceUpdate)).getOrThrow()
        }
        val nowPlayingMoviesAsync = scope.async {
            getNowPlayingMovies(NowPlayingMoviesParams(forceUpdate = forceUpdate)).getOrThrow()
        }
        val popularTvShowsAsync = scope.async {
            getPopularTvShows(PopularTvShowsParams(forceUpdate = forceUpdate)).getOrThrow()
        }
        val topRatedTvShowsAsync = scope.async {
            getTopRatedTvShows(TopRatedTvShowsParams(forceUpdate = forceUpdate)).getOrThrow()
        }
        val popularPeopleAsync = scope.async {
            getPopularPeople(PeopleParams(forceUpdate = forceUpdate)).getOrThrow()
        }

        val popularPeople = popularPeopleAsync.await()
        val popularMovies = popularMoviesAsync.await()
        val nowPlayingMovies = nowPlayingMoviesAsync.await()
        val topRatedTvShows = topRatedTvShowsAsync.await()

        upcomingMoviesAsync.await()
        topRatedMoviesAsync.await()
        popularTvShowsAsync.await()

        container.intent {
            container.reduce {
                HomeContract.State.Content(
                    list = getHomeScreenItemList(
                        nowPlayingMovies = nowPlayingMovies,
                        popularMovies = popularMovies,
                        popularTvShows = topRatedTvShows,
                        popularPeople = popularPeople
                    )
                )
            }
            container.postEffect(HomeContract.Effect.FinishRefresh)
        }
    }

    private fun handleError(e: Throwable) {
        container.intent {
            val cachedPeople = getCachedPopularPeople(Unit).getOrDefault(emptyList())
            val cachedPopularMovies =
                getCachedMoviesByType(CachedMoviesParams(MovieType.POPULAR)).getOrDefault(emptyList())
            val cachedNowPlayingMovies =
                getCachedMoviesByType(CachedMoviesParams(MovieType.NOW_PLAYING)).getOrDefault(
                    emptyList()
                )
            val cachedTvShows =
                getCachedTvShowsByType(CachedTvShowsParams(TvShowType.TOP_RATED)).getOrDefault(
                    emptyList()
                )

            val cachedList = getHomeScreenItemList(
                nowPlayingMovies = cachedNowPlayingMovies,
                popularMovies = cachedPopularMovies,
                popularTvShows = cachedTvShows,
                popularPeople = cachedPeople
            )

            if (cachedList.isEmpty()) {
                container.reduce {
                    HomeContract.State.Error(
                        list = listOf(
                            ErrorItem(
                                errorMessage = e.message.orEmpty(),
                                errorButtonVisible = true
                            )
                        ),
                        e = e
                    )
                }
            } else {
                container.reduce {
                    HomeContract.State.Content(list = cachedList)
                }
            }
            container.postEffect(HomeContract.Effect.FinishRefresh)
        }
    }

    private fun getHomeScreenItemList(
        nowPlayingMovies: List<MovieItem>,
        popularMovies: List<MovieItem>,
        popularTvShows: List<TvShowItem>,
        popularPeople: List<PersonItem>
    ): List<Model> {
        val list = mutableListOf<Model>()

        list.addAll(
            createNowPlayingMoviesPagerBlock(
                list = nowPlayingMovies,
                headerName = HomeResources.string.now_playing
            )
        )

        list.addAll(
            createMoviesBlock(
                list = popularMovies,
                headerName = HomeResources.string.popular_movies
            )
        )

        list.addAll(
            createTvShowsBlock(
                list = popularTvShows,
                headerName = HomeResources.string.popular_tv_shows
            )
        )

        list.addAll(
            createPeopleBlock(
                people = popularPeople,
                headerName = HomeResources.string.popular_people
            )
        )

        return list
    }

    private fun createMoviesBlock(
        list: List<MovieItem>,
        @StringRes headerName: Int,
        @StringRes headerLink: Int = R.string.any_screen_more
    ): List<Model> {
        return if (list.isNotEmpty()) {
            listOf(
                HeaderLinkItem(headerName, headerLink, HeaderLinkItemType.MOVIES),
                emptySpaceSmall,
                HorizontalListItem(list = list, id = MOVIES_LIST_ID),
                emptySpaceNormal
            )
        } else {
            emptyList()
        }
    }

    private fun createTvShowsBlock(
        list: List<TvShowItem>,
        @StringRes headerName: Int,
        @StringRes headerLink: Int = R.string.any_screen_more
    ): List<Model> {
        return if (list.isNotEmpty()) {
            listOf(
                HeaderLinkItem(headerName, headerLink, HeaderLinkItemType.TV_SHOWS),
                emptySpaceSmall,
                HorizontalListItem(list = list, id = TV_SHOWS_LIST_ID),
                emptySpaceNormal
            )
        } else {
            emptyList()
        }
    }

    private fun createPeopleBlock(
        people: List<PersonItem>,
        @StringRes headerName: Int,
        @StringRes headerLink: Int = R.string.any_screen_more,
    ): List<Model> {
        return if (people.isNotEmpty()) {
            val list = mutableListOf<Model>()
            list.add(HeaderLinkItem(headerName, headerLink, HeaderLinkItemType.PEOPLE))
            list.add(emptySpaceSmall)
            val items = people.map { PopularPersonItem(it) }
            list.add(HorizontalListItem(list = items, id = PEOPLE_LIST_ID))
            list.add(emptySpaceNormal)
            list
        } else {
            emptyList()
        }
    }

    private fun createNowPlayingMoviesPagerBlock(
        list: List<MovieItem>,
        @StringRes headerName: Int,
    ): List<Model> {
        val result = if (list.isNotEmpty()) {
            val items = list.map { PagerMovieItem(it) }
            val pagerItem = PagerItem(list = items)

            listOf(
                HeaderItem(headerName),
                emptySpaceNormal,
                pagerItem,
                emptySpaceNormal
            )
        } else {
            emptyList()
        }
        return result
    }

    fun refresh() {
        savedLoadData(forceUpdate = true)
    }

    fun openMovieDetail(itemId: Int) {
        container.intent {
            container.postEffect(HomeContract.Effect.OpenMovieDetail(Navigate { navigator ->
                (navigator as HomeNavigator).forwardToMovieDetailScreen(itemId)
            }))
        }
    }

    fun openPersonDetail(itemId: Int) {
        container.intent {
            container.postEffect(HomeContract.Effect.OpenPersonDetail(Navigate { navigator ->
                (navigator as HomeNavigator).forwardToPersonDetailScreen(itemId)
            }))
        }
    }

    fun openTvShowDetail(itemId: Int) {
        container.intent {
            container.postEffect(HomeContract.Effect.OpenTvShowDetail(Navigate { navigator ->
                (navigator as HomeNavigator).forwardToTvShowDetailScreen(itemId)
            }))
        }
    }

    fun openMore(type: HeaderLinkItemType) {
        val effect = when (type) {
            HeaderLinkItemType.MOVIES -> {
                HomeContract.Effect.OpenMovies(Navigate { navigator ->
                    (navigator as HomeNavigator).forwardToMoviesScreen()
                })
            }
            HeaderLinkItemType.PEOPLE -> HomeContract.Effect.OpenPeople(Navigate { navigator ->
                (navigator as HomeNavigator).forwardToPeopleScreen()
            })
            HeaderLinkItemType.TV_SHOWS -> HomeContract.Effect.OpenTvShows(Navigate { navigator ->
                (navigator as HomeNavigator).forwardToTvShowsScreen()
            })
        }

        container.intent {
            container.postEffect(effect)
        }
    }
}