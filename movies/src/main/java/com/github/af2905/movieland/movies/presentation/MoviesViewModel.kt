package com.github.af2905.movieland.movies.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.data.database.entity.MovieType
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.core.repository.TrendingRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = MoviesViewModel.Factory::class)
class MoviesViewModel @AssistedInject constructor(
    @Assisted private val movieType: MovieType,
    @Assisted private val movieId: Int?,
    private val moviesRepository: MoviesRepository,
    private val trendingRepository: TrendingRepository
) : ViewModel() {

    var state by mutableStateOf(MoviesState())
        private set

    private val _effect = Channel<MoviesEffect>()
    val effect = _effect.receiveAsFlow()

    fun onAction(action: MoviesAction) {
        when (action) {
            is MoviesAction.BackClick -> {
                viewModelScope.launch { _effect.send(MoviesEffect.NavigateBack) }
            }

            is MoviesAction.OpenMovieDetail -> {
                viewModelScope.launch { _effect.send(MoviesEffect.NavigateToMovieDetail(action.movieId)) }
            }

            is MoviesAction.RetryFetch -> fetchMovies()
        }
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            when(movieType) {
                MovieType.POPULAR, MovieType.NOW_PLAYING, MovieType.TOP_RATED, MovieType.UPCOMING -> {
                    val result = moviesRepository.getMovies(movieType = movieType, language = null, page = 1)

                }
                MovieType.TRENDING_DAY, MovieType.TRENDING_WEEK -> {
                    val result = trendingRepository.getTrendingMovies(movieType = movieType, language = null, page = 1)
                }

                MovieType.SIMILAR -> {
                    movieId?.let { id ->
                        moviesRepository.getSimilarMovies(movieId = id,  language = null, page = 1)
                    }
                }

                MovieType.RECOMMENDED -> {
                    movieId?.let { id ->
                        moviesRepository.getRecommendedMovies(movieId = id,  language = null, page = 1)
                    }
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            movieType: MovieType,
            movieId: Int?
        ): MoviesViewModel
    }
}
