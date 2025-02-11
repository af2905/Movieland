package com.github.af2905.movieland.movies.presentation

import com.github.af2905.movieland.core.data.database.entity.Movie

data class MoviesState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val popularMovies: List<Movie> = emptyList()
)

sealed interface MoviesAction {
    data object BackClick : MoviesAction
    data class OpenMovieDetail(val movieId: Int) : MoviesAction
    data object RetryFetch : MoviesAction
}

sealed interface MoviesEffect {
    data object NavigateBack : MoviesEffect
    data class NavigateToMovieDetail(val movieId: Int) : MoviesEffect
}