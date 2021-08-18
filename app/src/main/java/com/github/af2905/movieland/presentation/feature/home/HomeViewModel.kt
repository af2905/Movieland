package com.github.af2905.movieland.presentation.feature.home

import com.github.af2905.movieland.R
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies,
    private val getNowPlayingMovies: GetNowPlayingMovies,
    private val getTopRatedMovies: GetTopRatedMovies,
    private val getUpcomingMovies: GetUpcomingMovies,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : BaseViewModel<HomeNavigator>(coroutineDispatcherProvider) {

    private val _items = MutableStateFlow<List<Model>>(listOf())
    val items = _items.asStateFlow()

    private val _popular = MutableStateFlow<List<Model>>(listOf())
    private val _topRated = MutableStateFlow<List<Model>>(listOf())

    private val emptySpaceSmall = EmptySpaceItem(R.dimen.default_margin_small)
    private val emptySpaceNormal = EmptySpaceItem(R.dimen.default_margin)
    private val emptySpaceMedium = EmptySpaceItem(R.dimen.default_margin_medium)

    private fun starter() = listOf(LoadingItem())

    init {
        _items.tryEmit(starter())
        loadData()

    }

    private fun loadData() {
        launchIO {
            val list = mutableListOf<Model>()

            getPopularMovies(PopularMoviesParams()).let {
                list.addAll(
                    listOf(
                        HeaderItem(R.string.popular),
                        emptySpaceMedium,
                        HorizontalListItem(it.extractData?.movies ?: listOf()),
                        emptySpaceMedium
                    )
                )
            }
            getNowPlayingMovies(NowPlayingMoviesParams()).let {
                list.addAll(
                    listOf(
                        HeaderItem(R.string.now_playing),
                        emptySpaceMedium,
                        HorizontalListItem(it.extractData?.movies ?: listOf()),
                        emptySpaceMedium
                    )
                )
            }
            getTopRatedMovies(TopRatedMoviesParams()).let {
                it.extractData?.movies?.let { movies ->
                    list.addAll(
                        listOf(
                            HeaderItem(R.string.top_rated),
                            emptySpaceMedium,
                            HorizontalListItem(movies),
                            emptySpaceMedium
                        )
                    )

                    Timber.d("--------------> ${movies.size}")


                }
            }
            getUpcomingMovies(UpcomingMoviesParams()).let {
                list.addAll(
                    listOf(
                        HeaderItem(R.string.upcoming),
                        emptySpaceMedium,
                        HorizontalListItem(it.extractData?.movies ?: listOf()),
                        emptySpaceMedium
                    )
                )
            }
            _items.tryEmit(list)

        }


/*        val list = mutableListOf<Model>()

        launchIO(
            params = PopularMoviesParams(),
            execute = getPopularMovies::invoke
        ) {
            list.addAll(
                listOf(
                    HeaderItem(R.string.popular),
                    emptySpaceMedium,
                    HorizontalListItem(it.movies ?: listOf()),
                    emptySpaceMedium
                )
            )

        }
        launchIO(
            params = NowPlayingMoviesParams(),
            execute = getNowPlayingMovies::invoke
        ) {
            list.addAll(
                listOf(
                    HeaderItem(R.string.now_playing),
                    emptySpaceMedium,
                    HorizontalListItem(it.movies ?: listOf()),
                    emptySpaceMedium
                )
            )
        }
        launchIO(
            params = TopRatedMoviesParams(),
            execute = getTopRatedMovies::invoke
        ) {
            list.addAll(
                listOf(
                    HeaderItem(R.string.top_rated),
                    emptySpaceMedium,
                    HorizontalListItem(it.movies ?: listOf()),
                    emptySpaceMedium
                )
            )
        }
        launchIO(
            params = UpcomingMoviesParams(),
            execute = getUpcomingMovies::invoke
        ) {
            list.addAll(
                listOf(
                    HeaderItem(R.string.upcoming),
                    emptySpaceMedium,
                    HorizontalListItem(it.movies ?: listOf()),
                    emptySpaceMedium
                )
            )
        }
        list.add(emptySpaceMedium)
        _items.tryEmit(list)*/
    }


    /* private fun loadPopularMovies() {

         viewModelScope.launch {

             val list = mutableListOf<Model>()

             getPopularMovies(PopularMoviesParams()).let {
                 val horizontalListItem = HorizontalListItem(it.extractData?.movies ?: emptyList())
                 list.addAll(listOf(
                     HeaderItem(R.string.popular),
                     emptySpaceMedium,
                     horizontalListItem,
                     HeaderItem(R.string.now_playing),
                     horizontalListItem
                 ))
             }

             _items.tryEmit(list)
         }


*//*         launchIO(
             params = PopularMoviesParams(),
             execute = getPopularMovies::invoke
         ) {
             val horizontalListItem = HorizontalListItem(it.movies)
             val list = listOf(
                 HeaderItem(R.string.popular),
                 emptySpaceMedium,
                 horizontalListItem,
                 HeaderItem(R.string.now_playing),
                 horizontalListItem
             )



             _items.tryEmit(list)


             Timber.d("ITEMS---------------> ${items.value}")


             //_items.tryEmit(listOf(HeaderItem(R.string.popular), emptySpaceMedium, HeaderItem(R.string.now_playing)))


         }*//*
     }*/

    fun openDetail(item: MovieItem, position: Int) = navigate { forwardMovieDetail() }

}