package com.github.af2905.movieland.home.presentation

import androidx.lifecycle.ViewModel
import com.github.af2905.movieland.core.data.database.entity.Genre
import com.github.af2905.movieland.core.data.database.entity.GenreType
import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.database.entity.MovieType
import com.github.af2905.movieland.core.data.database.entity.TvShow
import com.github.af2905.movieland.core.data.database.entity.TvShowType
import com.github.af2905.movieland.core.repository.GenresRepository
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.core.repository.TrendingRepository
import com.github.af2905.movieland.core.repository.TvShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val tvShowsRepository: TvShowsRepository,
    private val trendingRepository: TrendingRepository,
    private val genresRepository: GenresRepository
) : ViewModel() {

    fun getMovies(): Flow<List<Movie>> {
        return moviesRepository.getMovies(
            movieType = MovieType.POPULAR,
            language = "en-US",
            page = 1,
        )
    }

    fun getTrendingMovies(): Flow<List<Movie>> {
        return trendingRepository.getTrendingMovies(
            movieType = MovieType.TRENDING_DAY,
            page = 1,
            language = "en-US",
        )
    }

    fun getGenres(): Flow<List<Genre>> {
        return genresRepository.getGenres(
            genreType = GenreType.TV_SHOW,
            language = "en-US",
        )
    }

    fun getTvShows(): Flow<List<TvShow>> {
        return tvShowsRepository.getTvShows(
            tvShowType = TvShowType.POPULAR,
            language = "en-US",
            page = 1,
        )
    }
}