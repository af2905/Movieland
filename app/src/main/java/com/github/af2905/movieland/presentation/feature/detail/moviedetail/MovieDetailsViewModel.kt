package com.github.af2905.movieland.presentation.feature.detail.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.af2905.movieland.R
import com.github.af2905.movieland.domain.usecase.movies.GetMovieActors
import com.github.af2905.movieland.domain.usecase.movies.GetMovieDetails
import com.github.af2905.movieland.domain.usecase.movies.GetSimilarMovies
import com.github.af2905.movieland.domain.usecase.params.MovieActorsParams
import com.github.af2905.movieland.domain.usecase.params.MovieDetailsParams
import com.github.af2905.movieland.domain.usecase.params.SimilarMoviesParams
import com.github.af2905.movieland.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.presentation.base.BaseViewModel
import com.github.af2905.movieland.presentation.feature.detail.DetailNavigator
import com.github.af2905.movieland.presentation.feature.detail.moviedetail.item.MovieDetailsDescItem
import com.github.af2905.movieland.presentation.feature.detail.moviedetail.item.MovieDetailsItem
import com.github.af2905.movieland.presentation.model.ItemIds
import com.github.af2905.movieland.presentation.model.Model
import com.github.af2905.movieland.presentation.model.item.*
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    args: MovieDetailsFragmentArgs,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val getMovieDetails: GetMovieDetails,
    private val getMovieActors: GetMovieActors,
    private val getSimilarMovies: GetSimilarMovies
) : BaseViewModel<DetailNavigator>(coroutineDispatcherProvider) {

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

        override fun onBackClicked() = navigate { back() }
    }

    init {
        loadData()
    }

    private fun loadData() {

        launchUI {
            loading.emit(true)
            val list = mutableListOf<Model>()
            if (movieDetailsItem.value == null) {
                val movieDetails = getMovieDetails(MovieDetailsParams(movieId))
                movieDetails.extractData?.let {
                    movieDetailsItem.postValue(it)
                    list.add(MovieDetailsDescItem(it))
                }
            }
            getMovieActors(MovieActorsParams(movieId)).let { result ->
                result.extractData?.let {
                    val actors = it.filterNot { actorItem -> actorItem.profilePath.isNullOrEmpty() }
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
            getSimilarMovies(SimilarMoviesParams((movieId))).let { result ->
                result.extractData?.let {
                    val similar = it.movies?.filterNot { movie -> movie.posterPath.isNullOrEmpty() }
                    if (!similar.isNullOrEmpty()) {
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
                }
            }
            _items.postValue(list)
            loading.emit(false)
        }
    }

    fun openActorDetail(item: MovieActorItem, position: Int) {}
    fun openSimilarMovieDetail(item: MovieItem, position: Int) =
        navigate { forwardMovieDetail(item.id) }
}