package com.github.af2905.movieland.core.repository

import com.github.af2905.movieland.core.data.database.entity.Genre
import com.github.af2905.movieland.core.data.database.entity.GenreType
import kotlinx.coroutines.flow.Flow

interface GenresRepository {
    fun getGenres(
        genreType: GenreType,
        language: String?
    ): Flow<List<Genre>>
}