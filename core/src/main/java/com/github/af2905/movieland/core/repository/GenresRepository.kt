package com.github.af2905.movieland.core.repository

import com.github.af2905.movieland.core.data.database.entity.Genres
import kotlinx.coroutines.flow.Flow

interface GenresRepository {
    fun getGenres(language: String?): Flow<List<Genres>>
}