package com.github.af2905.movieland.home.presentation

import androidx.lifecycle.ViewModel
import com.github.af2905.movieland.core.data.database.entity.Genres
import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.repository.GenresRepository
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.core.repository.TrendingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val trendingRepository: TrendingRepository,
    private val genresRepository: GenresRepository
) : ViewModel() {

    fun getMovies(): Flow<List<Movie>> {
        return moviesRepository.getMovies(
            movieType = "TOP_RATED",
            language = "en-US",
            page = 1,
        )
    }

    fun getTrendingMovies(): Flow<List<Movie>> {
        return trendingRepository.getTrendingMovies(
            timeWindow = "day",
            language = "en-US",
        )
    }

    fun getGenres(): Flow<List<Genres>> {
        return genresRepository.getGenres(
            language = "en-US",
        )
    }
}