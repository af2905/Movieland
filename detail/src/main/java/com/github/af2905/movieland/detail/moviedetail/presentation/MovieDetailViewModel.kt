package com.github.af2905.movieland.detail.moviedetail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.base.Container
import com.github.af2905.movieland.core.common.ErrorHandler
import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.effect.ToastMessage
import com.github.af2905.movieland.core.common.model.ItemIds
import com.github.af2905.movieland.core.common.model.Model
import com.github.af2905.movieland.core.common.model.item.*
import com.github.af2905.movieland.detail.R
import com.github.af2905.movieland.detail.moviedetail.MovieDetailNavigator
import com.github.af2905.movieland.detail.usecase.movie.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import java.util.Collections.emptyList
import javax.inject.Inject

private const val ACTORS_LIST_ID = ItemIds.HORIZONTAL_ITEM_LIST_ID * 1000 + 1
private const val SIMILAR_MOVIE_LIST_ID = ItemIds.HORIZONTAL_ITEM_LIST_ID * 1000 + 2

class MovieDetailViewModel @Inject constructor(
    private val movieId: Int,
    private val getMovieDetail: GetMovieDetail,
    private val getMovieCredits: GetMovieCredits,
    private val getSimilarMovies: GetSimilarMovies,
    private val saveToLikedMovie: SaveToLikedMovie,
    private val removeFromLikedMovie: RemoveFromLikedMovie,
    private val getLikedMovieById: GetLikedMovieById
) : ViewModel() {

    val container: Container<MovieDetailContract.State, MovieDetailContract.Effect> =
        Container(viewModelScope, MovieDetailContract.State.Loading)

    private val emptySpaceNormal = EmptySpaceItem(R.dimen.default_margin)
    private val emptySpaceBig = EmptySpaceItem(R.dimen.default_margin_big)
    private val emptySpaceHuge = EmptySpaceItem(R.dimen.default_margin_huge)

    val movieDetailItemClickListener = MovieDetailItem.Listener {
        container.intent {
            if (this.state.value is MovieDetailContract.State.Content) {
                val contentState = this.state.value as MovieDetailContract.State.Content
                handleLikeMovie(contentState)
            }
        }
    }

    val errorItemClickListener = ErrorItem.Listener { loadData() }
    val backButtonItemClickListener = BackButtonItem.Listener { openPreviousScreen() }

    init {
        loadData()
    }

    private suspend fun handleLikeMovie(state: MovieDetailContract.State.Content) {
        if (state.movieDetailItem.liked) {
            removeFromLikedMovie(UnlikedMovieDetailParams(state.movieDetailItem))
        } else {
            saveToLikedMovie(
                LikedMovieDetailParams(
                    state.movieDetailItem.copy(
                        liked = !state.movieDetailItem.liked
                    )
                )
            )
        }
        container.reduce {
            state.copy(
                movieDetailItem = state.movieDetailItem.copy(
                    liked = !state.movieDetailItem.liked
                ),
                list = state.list
            )
        }
        container.postEffect(
            MovieDetailContract.Effect.LikeClicked
        )
    }

    private fun loadData() {
        container.intent {
            if (container.state.value !is MovieDetailContract.State.Loading) {
                container.reduce { MovieDetailContract.State.Loading }
            }
            try {
                handleMovieDetail(viewModelScope)
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private suspend fun handleMovieDetail(scope: CoroutineScope) {
        val list = mutableListOf<Model>()
        var movieDetailItem = getLikedMovieById(LikedMovieDetailByIdParams(movieId)).getOrThrow()

        if (movieDetailItem == null) {
            val movieDetailAsync = scope.async {
                getMovieDetail(MovieDetailParams(movieId)).getOrThrow()
            }
            val movieCreditsCastAsync = scope.async {
                getMovieCredits(MovieCreditsParams(movieId)).getOrDefault(emptyList())
            }
            val similarMoviesAsync = scope.async {
                getSimilarMovies.invoke(SimilarMoviesParams(movieId)).getOrDefault(emptyList())
            }

            movieDetailItem = movieDetailAsync.await()
            val movieCreditsCasts = movieCreditsCastAsync.await()
            val similarMovies = similarMoviesAsync.await()

            val movieCreditCastsBlock = createActorsAndCrewBlock(movieCreditsCasts)
            val similarMoviesBlock = createSimilarMoviesBlock(similarMovies)

            //list.add(MovieDetailDescItem(movieDetailItem))
            list.addAll(movieCreditCastsBlock)
            list.addAll(similarMoviesBlock)
            movieDetailItem = movieDetailItem?.copy(
                creditsCasts = movieCreditsCasts,
                similarMovies = similarMovies
            )
        } else {
            list.add(MovieDetailDescItem(movieDetailItem))
            list.addAll(createActorsAndCrewBlock(movieDetailItem.creditsCasts))
            list.addAll(createSimilarMoviesBlock(movieDetailItem.similarMovies))
        }
        /*container.reduce {
            MovieDetailContract.State.Content(movieDetailItem = movieDetailItem, list = list)
        }*/
    }

    private fun createActorsAndCrewBlock(movieCreditsCasts: List<CreditsCastItem>): List<Model> {
        return if (movieCreditsCasts.isNotEmpty()) {
            listOf(
                emptySpaceNormal,
                HeaderItem(R.string.actors_and_crew_title),
                emptySpaceNormal,
                HorizontalListItem(movieCreditsCasts, id = ACTORS_LIST_ID),
                emptySpaceBig
            )
        } else {
            emptyList()
        }
    }

    private fun createSimilarMoviesBlock(similarMovies: List<MovieItem>): List<Model> {
        return if (similarMovies.isNotEmpty()) {
            listOf(
                HeaderItem(R.string.similar),
                emptySpaceNormal,
                HorizontalListItem(list = similarMovies, id = SIMILAR_MOVIE_LIST_ID),
                emptySpaceHuge
            )
        } else {
            emptyList()
        }
    }

    private suspend fun handleError(e: Exception) {
        container.reduce {
            MovieDetailContract.State.Error(
                errorItem = ErrorItem(errorMessage = e.message.orEmpty()),
                e = e
            )
        }
        container.intent {
            container.postEffect(
                MovieDetailContract.Effect.ShowFailMessage(
                    ToastMessage(ErrorHandler.handleError(e))
                )
            )
        }
    }

    fun navigateToMovieDetail(itemId: Int) {
        container.intent {
            container.postEffect(MovieDetailContract.Effect.OpenMovieDetail(Navigate { navigator ->
                (navigator as MovieDetailNavigator).forwardToMovieDetailScreen(itemId)
            }))
        }
    }

    fun navigateToPersonDetail(itemId: Int) {
        container.intent {
            container.postEffect(MovieDetailContract.Effect.OpenPersonDetail(Navigate { navigator ->
                (navigator as MovieDetailNavigator).forwardToPersonDetailScreen(itemId)
            }))
        }
    }

    private fun openPreviousScreen() {
        container.intent {
            container.postEffect(MovieDetailContract.Effect.OpenPreviousScreen(Navigate { navigator ->
                navigator.back()
            }))
        }
    }
}