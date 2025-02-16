package com.github.af2905.movieland.detail.moviedetail.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.common.network.ResultWrapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.detail.moviedetail.presentation.state.MovieDetailsAction
import com.github.af2905.movieland.detail.moviedetail.presentation.state.MovieDetailsEffect
import com.github.af2905.movieland.detail.moviedetail.presentation.state.MovieDetailsState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel

import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = MovieDetailsViewModel.Factory::class)
class MovieDetailsViewModel @AssistedInject constructor(
    @Assisted private val movieId: Int,
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    var state by mutableStateOf(MovieDetailsState())
        private set

    private val _effect = Channel<MovieDetailsEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        fetchMovieDetails()
    }

    private fun fetchMovieDetails() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            val movieDetailsResult = moviesRepository.getMovieDetails(movieId, null)

            val externalIds = (moviesRepository.getMovieExternalIds(
                movieId,
                null
            ) as? ResultWrapper.Success)?.data

            combine(
                moviesRepository.getMovieVideos(movieId, null),
                moviesRepository.getSimilarMovies(movieId, null, null),
                moviesRepository.getRecommendedMovies(movieId, null, null),
                moviesRepository.getMovieCredits(movieId, null)
            ) { videosResult, similarResult, recommendedResult, castsResult ->


                val videos = (videosResult as? ResultWrapper.Success)?.data ?: emptyList()
                val similarMovies = (similarResult as? ResultWrapper.Success)?.data ?: emptyList()
                val recommendedMovies =
                    (recommendedResult as? ResultWrapper.Success)?.data ?: emptyList()
                val casts = (castsResult as? ResultWrapper.Success)?.data ?: emptyList()
                
                state = state.copy(
                    movie = (movieDetailsResult as? ResultWrapper.Success)?.data,
                    isError = movieDetailsResult is ResultWrapper.Error,
                    movieSocialIds = state.movieSocialIds.copy(
                        wikidataId = externalIds?.wikidataId,
                        facebookId = externalIds?.facebookId,
                        instagramId = externalIds?.instagramId,
                        twitterId = externalIds?.twitterId
                    ),
                    videos = videos,
                    similarMovies = similarMovies,
                    recommendedMovies = recommendedMovies,
                    casts = casts,
                    isLoading = false
                )
            }.collect {}
        }
    }

    fun onAction(action: MovieDetailsAction) {
        when (action) {
            is MovieDetailsAction.BackClick -> {
                viewModelScope.launch { _effect.send(MovieDetailsEffect.NavigateBack) }
            }

            is MovieDetailsAction.OpenMovieDetail -> {
                viewModelScope.launch {
                    _effect.send(MovieDetailsEffect.NavigateToMovieDetail(action.movieId))
                }
            }

            is MovieDetailsAction.OpenPersonDetail -> {
                viewModelScope.launch { _effect.send(MovieDetailsEffect.NavigateToPerson(action.personId)) }
            }

            is MovieDetailsAction.OpenVideo -> {
                viewModelScope.launch { _effect.send(MovieDetailsEffect.NavigateToVideo(action.videoId)) }
            }

            is MovieDetailsAction.OpenMoviesByType -> {
                viewModelScope.launch {
                    _effect.send(
                        MovieDetailsEffect.NavigateToMovies(
                            movieId = movieId,
                            movieType = action.movieType
                        )
                    )
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(movieId: Int): MovieDetailsViewModel
    }
}