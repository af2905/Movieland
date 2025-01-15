package com.github.af2905.movieland.core.repository

import com.github.af2905.movieland.core.data.database.entity.Movie
import kotlinx.coroutines.flow.Flow

interface TrendingRepository {
    fun getTrendingMovies(timeWindow: String, language: String?): Flow<List<Movie>>
}