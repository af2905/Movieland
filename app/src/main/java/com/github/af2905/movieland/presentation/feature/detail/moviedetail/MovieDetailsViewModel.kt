package com.github.af2905.movieland.presentation.feature.detail.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.R
import com.github.af2905.movieland.domain.usecase.movies.GetMovieActors
import com.github.af2905.movieland.domain.usecase.movies.GetMovieDetails
import com.github.af2905.movieland.domain.usecase.movies.GetSimilarMovies
import com.github.af2905.movieland.helper.coroutine.CoroutineDispatcherProvider
import com.github.af2905.movieland.presentation.base.Container
import com.github.af2905.movieland.presentation.feature.detail.moviedetail.item.MovieDetailsItem
import com.github.af2905.movieland.presentation.model.Model
import com.github.af2905.movieland.presentation.model.item.EmptySpaceItem
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    args: MovieDetailsFragmentArgs,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val getMovieDetails: GetMovieDetails,
    private val getMovieActors: GetMovieActors,
    private val getSimilarMovies: GetSimilarMovies
) : ViewModel() {

    val container: Container<MovieDetailContract .State, MovieDetailContract .Effect> =
        Container(viewModelScope, MovieDetailContract.State.Loading)
    private val movieId = args.movieId

    private val emptySpaceSmall = EmptySpaceItem(R.dimen.default_margin_small)
    private val emptySpaceNormal = EmptySpaceItem(R.dimen.default_margin)
    private val emptySpaceMedium = EmptySpaceItem(R.dimen.default_margin_medium)
    private val emptySpaceBig = EmptySpaceItem(R.dimen.default_margin_big)

    private val _items = MutableLiveData<List<Model>>()
    val items: LiveData<List<Model>> = _items

    var movieDetailsItem = MutableLiveData<MovieDetailsItem>()

    val movieDetailsItemClickListener = object : MovieDetailsItem.Listener {
        override fun onLikedClick(item: MovieDetailsItem) {
            movieDetailsItem.postValue(
                movieDetailsItem.value?.copy(liked = !movieDetailsItem.value!!.liked)
            )
        }

        override fun onBackClicked(){} //= navigator { back() }
    }
/*

    init {
        loadData()
    }

    private fun loadData() {

        launchUI {
            //_loading.emit(true)
            val list = mutableListOf<Model>()
            if (movieDetailsItem.value == null) {
                val movieDetails = getMovieDetails(MovieDetailsParams(movieId))
                movieDetails.let {
                    movieDetailsItem.postValue(it.getOrThrow())
                    list.add(MovieDetailsDescItem(it.getOrThrow()))
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
            //_loading.emit(false)

*/
/*            getSimilarMovies.invoke(SimilarMoviesParams((movieId))).collect {
                when (val result = it.getOrThrow()) {
                    is UiState.Loading -> loading.emit(true)
                    is UiState.SuccessResult -> {

                        val similar = result.result
                            .map { model -> model as MovieItem }
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
                        _items.value = list
                        loading.emit(false)
                    }
                    else -> {}
                }
            }*//*

        }

    }

*/

/*    fun openActorDetail(item: MovieActorItem, position: Int) {}
    fun openSimilarMovieDetail(item: MovieItem, position: Int) =
        navigator { forwardMovieDetail(item.id) }*/
}