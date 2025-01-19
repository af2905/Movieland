package com.github.af2905.movieland.home.presentation

import com.github.af2905.movieland.core.data.database.entity.Genre
import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.database.entity.Person
import com.github.af2905.movieland.core.data.database.entity.TvShow

data class HomeState(
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

    val popularPeople: List<Person> = emptyList(),

    val horrorMovies: List<Movie> = emptyList(),
    val comedyMovies: List<Movie> = emptyList(),
    val actionMovies: List<Movie> = emptyList(),
    val dramaMovies: List<Movie> = emptyList(),
    val thrillerMovies: List<Movie> = emptyList(),
    val romanceMovies: List<Movie> = emptyList(),
    val adventureMovies: List<Movie> = emptyList(),
    val scienceFictionMovies: List<Movie> = emptyList(),

    val horrorTvShows: List<TvShow> = emptyList(),
    val comedyTvShows: List<TvShow> = emptyList(),
    val actionTvShows: List<TvShow> = emptyList(),
    val dramaTvShows: List<TvShow> = emptyList(),
    val thrillerTvShows: List<TvShow> = emptyList(),
    val romanceTvShows: List<TvShow> = emptyList(),
    val adventureTvShows: List<TvShow> = emptyList(),
    val scienceFictionTvShows: List<TvShow> = emptyList(),

    )

sealed interface HomeAction {
    data class OpenMovieDetail(val movieId: Int) : HomeAction
    data class OpenTvShowDetail(val tvShowId: Int) : HomeAction
    data class OpenPersonDetail(val personId: Int) : HomeAction
    data class OpenGenre(val genreId: Int) : HomeAction
}

sealed interface HomeEffect {
    data class NavigateToMovieDetail(val movieId: Int) : HomeEffect
    data class NavigateToTvShowDetail(val tvShowId: Int) : HomeEffect
    data class NavigateToPersonDetail(val personId: Int) : HomeEffect
    data class NavigateToGenre(val genreId: Int) : HomeEffect
}