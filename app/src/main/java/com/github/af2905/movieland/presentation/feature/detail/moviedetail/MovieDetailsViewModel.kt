package com.github.af2905.movieland.presentation.feature.detail.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.R
import com.github.af2905.movieland.domain.usecase.movies.GetMovieActors
import com.github.af2905.movieland.domain.usecase.movies.GetMovieDetails
import com.github.af2905.movieland.domain.usecase.movies.GetSimilarMovies
import com.github.af2905.movieland.domain.usecase.params.MovieActorsParams
import com.github.af2905.movieland.domain.usecase.params.MovieDetailsParams
import com.github.af2905.movieland.domain.usecase.params.SimilarMoviesParams
import com.github.af2905.movieland.helper.text.UiText
import com.github.af2905.movieland.presentation.base.Container
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        Container(viewModelScope, MovieDetailContract.State.Loading)

    private val emptySpaceNormal = EmptySpaceItem(R.dimen.default_margin)

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError = _isError.asStateFlow()

    private val _movieDetailsItem = MutableStateFlow(MovieDetailsItem())
    val movieDetailsItem = _movieDetailsItem.asStateFlow()

    private val _items = MutableStateFlow(emptyList<Model>())
    val items = _items.asStateFlow()

    val errorItem = ErrorItem()

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

    fun updateData() = loadData()

    fun openSimilarMovieDetail(itemId: Int) = navigateToDetail(itemId)

    suspend fun showLoading(show: Boolean) = _isLoading.emit(show)

    private suspend fun handleMovieDetail(scope: CoroutineScope) {
        val list = mutableListOf<Model>()

        val movieDetailsAsync = scope.async {
            MovieDetailsDescItem(getMovieDetails(MovieDetailsParams(movieId)).getOrThrow())
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
                        emptySpaceNormal
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
                        emptySpaceNormal
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

    suspend fun showContent(movieDetailsItem: MovieDetailsItem, list: List<Model>) {
        _isError.emit(false)
        _movieDetailsItem.emit(movieDetailsItem)
        _items.emit(list)
    }

    suspend fun showError(error: UiText) {
        _isError.emit(true)
        container.intent {
            container.postEffect(MovieDetailContract.Effect.ShowFailMessage(ToastMessage(error)))
        }
    }

    private suspend fun handleError(e: Exception) =
        container.reduce { MovieDetailContract.State.Error(e) }


    private fun navigateToDetail(itemId: Int) {
        container.intent {
            container.postEffect(MovieDetailContract.Effect.OpenMovieDetail(Navigate { navigator ->
                (navigator as DetailNavigator).forwardMovieDetail(itemId)
            }))
        }
    }
}