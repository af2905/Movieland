package com.github.af2905.movieland.presentation.feature.detail.moviedetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.R
import com.github.af2905.movieland.domain.usecase.movies.GetMovieActors
import com.github.af2905.movieland.domain.usecase.movies.GetMovieDetails
import com.github.af2905.movieland.domain.usecase.movies.GetSimilarMovies
import com.github.af2905.movieland.domain.usecase.params.MovieActorsParams
import com.github.af2905.movieland.domain.usecase.params.MovieDetailsParams
import com.github.af2905.movieland.domain.usecase.params.SimilarMoviesParams
import com.github.af2905.movieland.helper.coroutine.CoroutineDispatcherProvider
import com.github.af2905.movieland.presentation.base.Container
import com.github.af2905.movieland.presentation.feature.detail.moviedetail.item.MovieDetailsDescItem
import com.github.af2905.movieland.presentation.feature.detail.moviedetail.item.MovieDetailsItem
import com.github.af2905.movieland.presentation.model.ItemIds
import com.github.af2905.movieland.presentation.model.Model
import com.github.af2905.movieland.presentation.model.item.EmptySpaceItem
import com.github.af2905.movieland.presentation.model.item.HeaderItem
import com.github.af2905.movieland.presentation.model.item.HorizontalListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    args: MovieDetailsFragmentArgs,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val getMovieDetails: GetMovieDetails,
    private val getMovieActors: GetMovieActors,
    private val getSimilarMovies: GetSimilarMovies
) : ViewModel() {

    val container: Container<MovieDetailContract.State, MovieDetailContract.Effect> =
        Container(viewModelScope, MovieDetailContract.State.Loading())
    private val movieId = args.movieId

    private val emptySpaceSmall = EmptySpaceItem(R.dimen.default_margin_small)
    private val emptySpaceNormal = EmptySpaceItem(R.dimen.default_margin)
    private val emptySpaceMedium = EmptySpaceItem(R.dimen.default_margin_medium)
    private val emptySpaceBig = EmptySpaceItem(R.dimen.default_margin_big)

    private val _items = MutableStateFlow<List<Model>>(listOf())
    val items = _items.asStateFlow()

    var movieDetailsItem = MutableLiveData<MovieDetailsItem>()

    val movieDetailsItemClickListener = object : MovieDetailsItem.Listener {
        override fun onLikedClick(item: MovieDetailsItem) {
            movieDetailsItem.postValue(
                movieDetailsItem.value?.copy(liked = !movieDetailsItem.value!!.liked)
            )
        }

        override fun onBackClicked() {} //= navigator { back() }
    }

    init {
        loadData()
    }

    /*    private fun loadData(forceUpdate: Boolean = false) {
        container.intent {
            container.reduce { HomeContract.State.Loading(nowPlayingMovies.value) }
            try {
                getNowPlayingMovies(NowPlayingMoviesParams(forceUpdate = forceUpdate)).let { result ->
                    container.reduce {
                        result.getOrThrow().let {
                            if (it.isEmpty()) HomeContract.State.EmptyResult
                            else HomeContract.State.Success(it)
                        }
                    }
                }
            } catch (e: Exception) {
                container.reduce { HomeContract.State.Error(e) }
            }
        }
    }*/

    private fun loadData() {
        container.intent {
            val list = mutableListOf<Model>()
            try {
                if (movieDetailsItem.value == null) {
                    val movieDetails = getMovieDetails(MovieDetailsParams(movieId))
                    movieDetails.let {
                        val result = it.getOrThrow()
                        movieDetailsItem.postValue(result)
                        list.add(MovieDetailsDescItem(result))
                    }
                }
                getMovieActors(MovieActorsParams(movieId)).let { result ->
                    result.let {
                        val actors = it.getOrThrow()
                            .filterNot { actorItem -> actorItem.profilePath.isNullOrEmpty() }
                        list.addAll(
                            listOf(
                                HeaderItem(R.string.actors_and_crew_title),
                                emptySpaceNormal,
                                HorizontalListItem(
                                    actors,
                                    id = ItemIds.HORIZONTAL_ITEM_LIST_ID * 1000 + 1
                                ),
                                emptySpaceNormal
                            )
                        )
                    }
                }
                getSimilarMovies.invoke(SimilarMoviesParams((movieId))).let { result ->
                    val similar = result.getOrThrow()
                        .filterNot { movieItem -> movieItem.posterPath.isNullOrEmpty() }
                    list.addAll(
                        listOf(
                            HeaderItem(R.string.similar),
                            emptySpaceNormal,
                            HorizontalListItem(
                                similar,
                                id = ItemIds.HORIZONTAL_ITEM_LIST_ID * 1000 + 2
                            )
                        )
                    )
                }
                container.reduce {
                    MovieDetailContract.State.Success(list)
                }
            } catch (e: Exception) {
                container.reduce { MovieDetailContract.State.Error(e) }
            }
        }


    }

/*    fun openActorDetail(item: MovieActorItem, position: Int) {}
    fun openSimilarMovieDetail(item: MovieItem, position: Int) =
        navigator { forwardMovieDetail(item.id) }*/

    fun updateSuccessData(items: List<Model>) {
        _items.tryEmit(items)
    }
}