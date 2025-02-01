package com.github.af2905.movieland.detail.moviedetail.presentation

import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.database.entity.MovieDetail
import com.github.af2905.movieland.core.data.database.entity.Video

data class MovieDetailsState(
    val movie: MovieDetail? = null,
    val similarMovies: List<Movie>? = null,
    val videos: List<Video> = emptyList()
)

sealed interface MovieDetailsAction {
    data object BackClick : MovieDetailsAction
    data class OpenMovieDetail(val movieId: Int) : MovieDetailsAction
    data class OpenGenre(val genreId: Int) : MovieDetailsAction
    data class OpenVideo(val videoId: String) : MovieDetailsAction
}

sealed interface MovieDetailsEffect {
    data object NavigateBack : MovieDetailsEffect
    data class NavigateToMovieDetail(val movieId: Int) : MovieDetailsEffect
    data class NavigateToGenre(val genreId: Int) : MovieDetailsEffect
    data class NavigateToVideo(val videoId: String) : MovieDetailsEffect
}