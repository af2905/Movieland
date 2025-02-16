package com.github.af2905.movieland.movies.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.database.entity.MovieType
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.core.repository.TrendingRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
@HiltViewModel(assistedFactory = MoviesViewModel.Factory::class)
class MoviesViewModel @AssistedInject constructor(
    @Assisted private val movieId: Int?,
    @Assisted private val movieType: MovieType,
    moviesRepository: MoviesRepository,
    trendingRepository: TrendingRepository
) : ViewModel() {

    var state by mutableStateOf(MoviesState(movieType = movieType))
        private set

    private val _effect = Channel<MoviesEffect>()
    val effect = _effect.receiveAsFlow()

    val moviesPager: Flow<PagingData<Movie>> = when {
        movieType == MovieType.SIMILAR && movieId != null -> {
            moviesRepository.getSimilarOrRecommendedPaginated(
                movieId = movieId,
                movieType = movieType,
                language = null
            )
        }
        movieType == MovieType.RECOMMENDED && movieId != null -> {
            moviesRepository.getSimilarOrRecommendedPaginated(
                movieId = movieId,
                movieType = movieType,
                language = null
            )
        }
        movieType == MovieType.TRENDING_DAY || movieType == MovieType.TRENDING_WEEK -> {
            trendingRepository.getTrendingMoviesPaginated(movieType, language = null)
        }
        else -> {
            moviesRepository.getMoviesPaginated(movieType = movieType, language = null)
        }
    }.cachedIn(viewModelScope)


    fun onAction(action: MoviesAction) {
        when (action) {
            is MoviesAction.BackClick -> {
                viewModelScope.launch { _effect.send(MoviesEffect.NavigateBack) }
            }
            is MoviesAction.OpenMovieDetail -> {
                viewModelScope.launch { _effect.send(MoviesEffect.NavigateToMovieDetail(action.movieId)) }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            movieId: Int?,
            movieType: MovieType
        ): MoviesViewModel
    }
}