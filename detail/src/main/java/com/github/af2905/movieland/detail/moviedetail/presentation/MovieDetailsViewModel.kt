package com.github.af2905.movieland.detail.moviedetail.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.repository.MoviesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
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
        viewModelScope.launch {
            val result = moviesRepository.getMovieDetails(movieId, null)
            state = state.copy(movie = result)
        }
        viewModelScope.launch {
            moviesRepository.getSimilarMovies(movieId, language = null, page = null)
                .collectLatest {
                    state = state.copy(similarMovies = it)
                }
        }
        viewModelScope.launch {
            moviesRepository.getRecommendedMovies(movieId, language = null, page = null)
                .collectLatest {
                    state = state.copy(recommendedMovies = it)
                }
        }
        viewModelScope.launch {
            moviesRepository.getMovieVideos(movieId, language = null)
                .collectLatest { videoList ->
                    state = state.copy(videos = videoList)
                }
        }
        viewModelScope.launch {
            moviesRepository.getMovieCredits(movieId, language = null).collectLatest { castList ->
                state = state.copy(casts = castList)
            }
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
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(movieId: Int): MovieDetailsViewModel
    }
}