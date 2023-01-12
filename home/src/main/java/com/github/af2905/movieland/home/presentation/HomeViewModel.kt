package com.github.af2905.movieland.home.presentation

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.base.Container
import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.model.ItemIds
import com.github.af2905.movieland.core.common.model.Model
import com.github.af2905.movieland.core.common.model.item.*
import com.github.af2905.movieland.core.shared.*
import com.github.af2905.movieland.detail.R
import com.github.af2905.movieland.home.domain.params.TvShowsParams
import com.github.af2905.movieland.home.domain.usecase.GetPopularTvShows
import com.github.af2905.movieland.home.domain.usecase.GetTopRatedTvShows
import com.github.af2905.movieland.home.presentation.item.PagerMovieItem
import com.github.af2905.movieland.home.presentation.item.PopularPersonItem
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
) : ViewModel() {

    val container: Container<HomeContract.State, HomeContract.Effect> =
        Container(viewModelScope, HomeContract.State.Loading())

    private val emptySpaceSmall = EmptySpaceItem(R.dimen.default_margin_small)
    private val emptySpaceNormal = EmptySpaceItem(R.dimen.default_margin)

    init {
        loadData()
    }

    private fun loadData(forceUpdate: Boolean = false) {
        viewModelScope.launch {

            val popularMoviesAsync = this.async {
                getPopularMovies(PopularMoviesParams(forceUpdate = forceUpdate))
                    .getOrDefault(emptyList())
            }
            val upcomingMoviesAsync = this.async {
                getUpcomingMovies(UpcomingMoviesParams(forceUpdate = forceUpdate))
                    .getOrDefault(emptyList())
            }
            val topRatedMoviesAsync = this.async {
                getTopRatedMovies(TopRatedMoviesParams(forceUpdate = forceUpdate))
                    .getOrDefault(emptyList())
            }
            val nowPlayingMoviesAsync = this.async {
                getNowPlayingMovies(NowPlayingMoviesParams(forceUpdate = forceUpdate))
                    .getOrDefault(emptyList())
            }
            val popularTvShowsAsync = this.async {
                getPopularTvShows(TvShowsParams(forceUpdate = forceUpdate))
                    .getOrDefault(emptyList())
            }
            val topRatedTvShowsAsync = this.async {
                getTopRatedTvShows(TvShowsParams(forceUpdate = forceUpdate))
                    .getOrDefault(emptyList())
            }
            val popularPeopleAsync = this.async {
                getPopularPeople(PeopleParams(forceUpdate = forceUpdate))
                    .getOrDefault(emptyList())
            }

            val popularPeople = popularPeopleAsync.await()
            val popularMovies = popularMoviesAsync.await()
            val nowPlayingMovies = nowPlayingMoviesAsync.await()
            val topRatedTvShows = topRatedTvShowsAsync.await()

            upcomingMoviesAsync.await()
            topRatedMoviesAsync.await()
            popularTvShowsAsync.await()

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
                    list = topRatedTvShows,
                    headerName = HomeResources.string.popular_tv_shows
                )
            )

            list.addAll(
                createPeopleBlock(
                    people = popularPeople,
                    headerName = HomeResources.string.popular_people
                )
            )

            val currentState = if (list.isEmpty()) {
                HomeContract.State.Empty()
            } else {
                HomeContract.State.Content(list = list)
            }

            container.intent {
                container.reduce { currentState }
                container.postEffect(HomeContract.Effect.FinishRefresh)
            }
        }
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
            listOf(
                HeaderItem(headerName),
                emptySpaceNormal,
                PagerItem(list = items),
                emptySpaceNormal
            )
        } else {
            emptyList()
        }
        return result
    }

    fun refresh() = loadData(forceUpdate = true)

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
            HeaderLinkItemType.TV_SHOWS -> HomeContract.Effect.OpenTvShows(Navigate { })
        }

        container.intent {
            container.postEffect(effect)
        }
    }
}