package com.github.af2905.movieland.home.presentation.state

import com.github.af2905.movieland.compose.theme.Themes
import com.github.af2905.movieland.core.data.database.entity.Genre
import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.database.entity.MovieType
import com.github.af2905.movieland.core.data.database.entity.Person
import com.github.af2905.movieland.core.data.database.entity.TvShow
import com.github.af2905.movieland.core.data.database.entity.TvShowType

data class HomeState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val trendingMovies: List<Movie> = emptyList(),
    val trendingTvShows: List<TvShow> = emptyList(),
    val trendingPeople: List<Person> = emptyList(),
    val popularMovies: List<Movie> = emptyList(),
    val topRatedMovies: List<Movie> = emptyList(),
    val upcomingMovies: List<Movie> = emptyList(),
    val nowPlayingMovies: List<Movie> = emptyList(),
    val popularTvShows: List<TvShow> = emptyList(),
    val topRatedTvShows: List<TvShow> = emptyList(),
    val moviesGenres: List<Genre> = emptyList(),
    val tvShowsGenres: List<Genre> = emptyList(),
    val popularPeople: List<Person> = emptyList()
)

sealed interface HomeAction {
    data class OpenMovieDetail(val movieId: Int) : HomeAction
    data class OpenTvShowDetail(val tvShowId: Int) : HomeAction
    data class OpenPersonDetail(val personId: Int) : HomeAction
    data class OpenGenre(val genreId: Int) : HomeAction
    data class ChangeAppColor(val selectedTheme: Themes) : HomeAction
    data object RetryFetch : HomeAction
    data class OpenMoviesByType(val movieType: MovieType) : HomeAction
    data class OpenTvShowsByType(val tvShowType: TvShowType) : HomeAction
}

sealed interface HomeEffect {
    data class NavigateToMovieDetail(val movieId: Int) : HomeEffect
    data class NavigateToTvShowDetail(val tvShowId: Int) : HomeEffect
    data class NavigateToPersonDetail(val personId: Int) : HomeEffect
    data class NavigateToGenre(val genreId: Int) : HomeEffect
    data class ChangeAppColor(val selectedTheme: Themes) : HomeEffect
    data class NavigateToMovies(val movieType: MovieType) : HomeEffect
    data class NavigateToTvShows(val tvShowType: TvShowType) : HomeEffect
}