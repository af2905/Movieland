package com.github.af2905.movieland.detail.moviedetail.presentation

import com.github.af2905.movieland.core.data.database.entity.CreditsCast
import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.database.entity.MovieDetail
import com.github.af2905.movieland.core.data.database.entity.Video

data class MovieDetailsState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val movie: MovieDetail? = null,
    val similarMovies: List<Movie> = emptyList(),
    val recommendedMovies: List<Movie> = emptyList(),
    val videos: List<Video> = emptyList(),
    val casts: List<CreditsCast> = emptyList(),
    val movieSocialIds: MovieSocialIds = MovieSocialIds()
) {
    data class MovieSocialIds(
        val wikidataId: String? = null,
        val facebookId: String? = null,
        val instagramId: String? = null,
        val twitterId: String? = null
    )
}

sealed interface MovieDetailsAction {
    data object BackClick : MovieDetailsAction
    data class OpenMovieDetail(val movieId: Int) : MovieDetailsAction
    data class OpenPersonDetail(val personId: Int) : MovieDetailsAction
    data class OpenVideo(val videoId: String) : MovieDetailsAction
}

sealed interface MovieDetailsEffect {
    data object NavigateBack : MovieDetailsEffect
    data class NavigateToMovieDetail(val movieId: Int) : MovieDetailsEffect
    data class NavigateToPerson(val personId: Int) : MovieDetailsEffect
    data class NavigateToVideo(val videoId: String) : MovieDetailsEffect
}