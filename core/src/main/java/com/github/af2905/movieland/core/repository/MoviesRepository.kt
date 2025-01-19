package com.github.af2905.movieland.core.repository

import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.database.entity.MovieType
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getMovies(
        movieType: MovieType,
        language: String?,
        page: Int?
    ): Flow<List<Movie>>
}