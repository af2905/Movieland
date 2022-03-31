package com.github.af2905.movieland.presentation.feature.detail.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.R
import com.github.af2905.movieland.domain.usecase.movies.GetMovieActors
import com.github.af2905.movieland.domain.usecase.movies.GetMovieDetails
import com.github.af2905.movieland.domain.usecase.movies.GetSimilarMovies
import com.github.af2905.movieland.domain.usecase.params.MovieActorsParams
import com.github.af2905.movieland.domain.usecase.params.MovieDetailsParams
import com.github.af2905.movieland.domain.usecase.params.SimilarMoviesParams
import com.github.af2905.movieland.presentation.base.Container
import com.github.af2905.movieland.presentation.common.ErrorHandler
import com.github.af2905.movieland.presentation.common.effect.Navigate
import com.github.af2905.movieland.presentation.common.effect.ToastMessage
import com.github.af2905.movieland.presentation.feature.detail.DetailNavigator
import com.github.af2905.movieland.presentation.feature.detail.moviedetail.item.MovieDetailsDescItem
import com.github.af2905.movieland.presentation.feature.detail.moviedetail.item.MovieDetailsItem
import com.github.af2905.movieland.presentation.model.ItemIds
import com.github.af2905.movieland.presentation.model.Model
import com.github.af2905.movieland.presentation.model.item.EmptySpaceItem
import com.github.af2905.movieland.presentation.model.item.ErrorItem
import com.github.af2905.movieland.presentation.model.item.HeaderItem
import com.github.af2905.movieland.presentation.model.item.HorizontalListItem
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val ACTORS_LIST_ID = ItemIds.HORIZONTAL_ITEM_LIST_ID * 1000 + 1
private const val SIMILAR_MOVIE_LIST_ID = ItemIds.HORIZONTAL_ITEM_LIST_ID * 1000 + 2

class MovieDetailsViewModel @Inject constructor(
    args: MovieDetailsFragmentArgs,
    private val getMovieDetails: GetMovieDetails,
    private val getMovieActors: GetMovieActors,
    private val getSimilarMovies: GetSimilarMovies
) : ViewModel() {

    private val movieId = args.movieId

    val container: Container<MovieDetailContract.State, MovieDetailContract.Effect> =
        Container(viewModelScope, MovieDetailContract.State.Content(isLoading = true))

    private val emptySpaceNormal = EmptySpaceItem(R.dimen.default_margin)

    val isLoading = container.state
        .filter { it is MovieDetailContract.State.Content }
        .map { (it as MovieDetailContract.State.Content).isLoading }
        .asLiveData()

    val movieDetailsItem = container.state
        .filter { it is MovieDetailContract.State.Content }
        .map { (it as MovieDetailContract.State.Content).movieDetailsItem }
        .asLiveData()

    val items = container.state
        .filter { it is MovieDetailContract.State.Content }
        .map { (it as MovieDetailContract.State.Content).list }
        .asLiveData()

    val isError = container.state
        .filter { it is MovieDetailContract.State.Content }
        .map { (it as MovieDetailContract.State.Content).error != null }
        .asLiveData()

    val movieDetailsItemClickListener = MovieDetailsItem.Listener {
        container.intent {
            container.reduce {
                if (this is MovieDetailContract.State.Content) {
                    MovieDetailContract.State.Content(
                        movieDetailsItem = this.movieDetailsItem?.copy(
                            liked = !this.movieDetailsItem.liked
                        ),
                        list = this.list
                    )
                } else {
                    this
                }
            }
        }
    }

    init {
        loadData()
    }

    private fun loadData() {
        container.intent {
            try {
                handleMovieDetail()
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun updateData() = loadData()

    fun openSimilarMovieDetail(itemId: Int) = navigateToDetail(itemId)

    private suspend fun handleMovieDetail() {
        val list = mutableListOf<Model>()
        getMovieDetails(MovieDetailsParams(movieId)).let {
            val result = it.getOrThrow()
            list.add(MovieDetailsDescItem(result))
        }
        getMovieActors(MovieActorsParams(movieId)).let { result ->
            val actors = result
                .getOrDefault(emptyList())
                .filterNot { actorItem -> actorItem.profilePath.isNullOrEmpty() }
            if (actors.isNotEmpty()) {
                list.addAll(
                    listOf(
                        emptySpaceNormal,
                        HeaderItem(R.string.actors_and_crew_title),
                        emptySpaceNormal,
                        HorizontalListItem(actors, id = ACTORS_LIST_ID),
                        emptySpaceNormal
                    )
                )
            }
        }
        getSimilarMovies.invoke(SimilarMoviesParams(movieId)).let { result ->
            val similar = result
                .getOrDefault(emptyList())
                .filterNot { movieItem -> movieItem.posterPath.isNullOrEmpty() }
            if (similar.isNotEmpty()) {
                list.addAll(
                    listOf(
                        HeaderItem(R.string.similar),
                        emptySpaceNormal,
                        HorizontalListItem(similar, id = SIMILAR_MOVIE_LIST_ID),
                        emptySpaceNormal
                    )
                )
            }
        }
        val movieDetailsDescItem = list.find { it is MovieDetailsDescItem } as MovieDetailsDescItem

        container.reduce {
            MovieDetailContract.State.Content(
                isLoading = false,
                movieDetailsItem = movieDetailsDescItem.movieDetailsItem,
                list = list,
                error = null
            )
        }
    }

    private suspend fun handleError(e: Exception) {
        container.reduce {
            MovieDetailContract.State.Content(
                isLoading = false,
                movieDetailsItem = null,
                list = listOf<Model>(ErrorItem()),
                error = e
            )
        }
        container.postEffect(
            MovieDetailContract.Effect.ShowFailMessage(
                ToastMessage(
                    ErrorHandler.handleError(e)
                )
            )
        )
    }

    private fun navigateToDetail(itemId: Int) {
        container.intent {
            container.postEffect(MovieDetailContract.Effect.OpenMovieDetail(Navigate { navigator ->
                (navigator as DetailNavigator).forwardMovieDetail(itemId)
            }))
        }
    }
}