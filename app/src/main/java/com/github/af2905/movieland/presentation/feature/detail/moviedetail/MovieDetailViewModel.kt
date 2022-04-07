package com.github.af2905.movieland.presentation.feature.detail.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.R
import com.github.af2905.movieland.domain.usecase.movies.GetMovieActors
import com.github.af2905.movieland.domain.usecase.movies.GetMovieDetail
import com.github.af2905.movieland.domain.usecase.movies.GetSimilarMovies
import com.github.af2905.movieland.domain.usecase.params.MovieActorsParams
import com.github.af2905.movieland.domain.usecase.params.MovieDetailsParams
import com.github.af2905.movieland.domain.usecase.params.SimilarMoviesParams
import com.github.af2905.movieland.presentation.base.Container
import com.github.af2905.movieland.presentation.common.ErrorHandler
import com.github.af2905.movieland.presentation.common.effect.Navigate
import com.github.af2905.movieland.presentation.common.effect.ToastMessage
import com.github.af2905.movieland.presentation.feature.detail.moviedetail.item.MovieDetailDescItem
import com.github.af2905.movieland.presentation.feature.detail.moviedetail.item.MovieDetailsItem
import com.github.af2905.movieland.presentation.model.ItemIds
import com.github.af2905.movieland.presentation.model.Model
import com.github.af2905.movieland.presentation.model.item.EmptySpaceItem
import com.github.af2905.movieland.presentation.model.item.ErrorItem
import com.github.af2905.movieland.presentation.model.item.HeaderItem
import com.github.af2905.movieland.presentation.model.item.HorizontalListItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import javax.inject.Inject

private const val ACTORS_LIST_ID = ItemIds.HORIZONTAL_ITEM_LIST_ID * 1000 + 1
private const val SIMILAR_MOVIE_LIST_ID = ItemIds.HORIZONTAL_ITEM_LIST_ID * 1000 + 2

class MovieDetailViewModel @Inject constructor(
    args: MovieDetailFragmentArgs,
    private val getMovieDetail: GetMovieDetail,
    private val getMovieActors: GetMovieActors,
    private val getSimilarMovies: GetSimilarMovies
) : ViewModel() {

    private val movieId = args.movieId

    val container: Container<MovieDetailContract.State, MovieDetailContract.Effect> =
        Container(viewModelScope, MovieDetailContract.State.Loading)

    private val emptySpaceNormal = EmptySpaceItem(R.dimen.default_margin)
    private val emptySpaceBig = EmptySpaceItem(R.dimen.default_margin_big)
    private val emptySpaceHuge = EmptySpaceItem(R.dimen.default_margin_huge)

    val movieDetailsItemClickListener = MovieDetailsItem.Listener {
        container.intent {
            container.reduce {
                if (this is MovieDetailContract.State.Content) {
                    MovieDetailContract.State.Content(
                        movieDetailsItem = this.movieDetailsItem.copy(
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

    val errorItemClickListener = ErrorItem.Listener { loadData() }

    init {
        loadData()
    }

    private fun loadData() {
        container.intent {
            try {
                handleMovieDetail(viewModelScope)
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun openSimilarMovieDetail(itemId: Int) = navigateToDetail(itemId)

    private suspend fun handleMovieDetail(scope: CoroutineScope) {
        val list = mutableListOf<Model>()

        val movieDetailsAsync = scope.async {
            MovieDetailDescItem(getMovieDetail(MovieDetailsParams(movieId)).getOrThrow())
        }

        val movieActorsAsync = scope.async {
            getMovieActors(MovieActorsParams(movieId)).let { result ->
                val actors = result
                    .getOrDefault(emptyList())
                    .filterNot { actorItem -> actorItem.profilePath.isNullOrEmpty() }
                if (actors.isNotEmpty()) {
                    listOf(
                        emptySpaceNormal,
                        HeaderItem(R.string.actors_and_crew_title),
                        emptySpaceNormal,
                        HorizontalListItem(actors, id = ACTORS_LIST_ID),
                        emptySpaceBig
                    )
                } else {
                    emptyList()
                }
            }
        }

        val similarMoviesAsync = scope.async {
            getSimilarMovies.invoke(SimilarMoviesParams(movieId)).let { result ->
                val similar = result
                    .getOrDefault(emptyList())
                    .filterNot { movieItem -> movieItem.posterPath.isNullOrEmpty() }
                if (similar.isNotEmpty()) {
                    listOf(
                        HeaderItem(R.string.similar),
                        emptySpaceNormal,
                        HorizontalListItem(similar, id = SIMILAR_MOVIE_LIST_ID),
                        emptySpaceHuge
                    )
                } else {
                    emptyList()
                }
            }
        }

        val movieDetails = movieDetailsAsync.await()
        val movieActors = movieActorsAsync.await()
        val similarMovies = similarMoviesAsync.await()

        list.add(movieDetails)
        list.addAll(movieActors)
        list.addAll(similarMovies)

        container.reduce {
            MovieDetailContract.State.Content(
                movieDetailsItem = movieDetails.movieDetailsItem,
                list = list
            )
        }
    }

    private suspend fun handleError(e: Exception) {
        container.reduce {
            MovieDetailContract.State.Error(
                ErrorItem(errorMessage = e.message.orEmpty()), e)
        }
        container.intent {
            container.postEffect(
                MovieDetailContract.Effect.ShowFailMessage(
                    ToastMessage(ErrorHandler.handleError(e))
                )
            )
        }
    }

    private fun navigateToDetail(itemId: Int) {
        container.intent {
            container.postEffect(MovieDetailContract.Effect.OpenMovieDetail(Navigate { navigator ->
                (navigator as MovieDetailNavigator).forwardMovieDetail(itemId)
            }))
        }
    }
}