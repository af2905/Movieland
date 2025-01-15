package com.github.af2905.movieland.core.repository

import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.database.entity.MovieDetail
import com.github.af2905.movieland.core.data.database.entity.MovieType
import com.github.af2905.movieland.core.data.dto.CreditsCastDto
import com.github.af2905.movieland.core.data.dto.movie.MovieDetailDto
import com.github.af2905.movieland.core.data.dto.movie.MovieDto
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getMovies(
        movieType: String,
        language: String?,
        page: Int?
    ): Flow<List<Movie>>
}