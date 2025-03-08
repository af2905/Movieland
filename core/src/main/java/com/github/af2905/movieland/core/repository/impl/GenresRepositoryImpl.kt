package com.github.af2905.movieland.core.repository.impl

import com.github.af2905.movieland.core.data.api.GenresApi
import com.github.af2905.movieland.core.data.database.dao.GenresDao
import com.github.af2905.movieland.core.data.database.entity.Genre
import com.github.af2905.movieland.core.data.database.entity.GenreType
import com.github.af2905.movieland.core.repository.GenresRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GenresRepositoryImpl @Inject constructor(
    private val genresApi: GenresApi,
    private val genresDao: GenresDao
) : GenresRepository {

    companion object {
        private const val CACHE_VALIDITY_PERIOD = 7 * 24 * 60 * 60 * 1000 // 1 week in milliseconds
    }

    override fun getGenres(
        genreType: GenreType,
        language: String?
    ): Flow<List<Genre>> = flow {
        // Retrieve cached genres
        val cachedGenres = genresDao.getGenresByType(genreType).firstOrNull()
        val lastUpdated = cachedGenres?.firstOrNull()?.timeStamp ?: 0L
        val isCacheStale = System.currentTimeMillis() - lastUpdated > CACHE_VALIDITY_PERIOD

        // Fetch fresh data if cache is stale
        if (cachedGenres.isNullOrEmpty() || isCacheStale) {
            try {
                val response = when (genreType) {
                    GenreType.MOVIE -> genresApi.getMovieGenres(language = language)
                    GenreType.TV_SHOW -> genresApi.getTvShowGenres(language = language)
                }

                val genres = response.genres.map { genreDto ->
                    Genre(
                        id = genreDto.id,
                        name = genreDto.name,
                        genreType = genreType,
                        timeStamp = System.currentTimeMillis()
                    )
                }

                if (genres.isNotEmpty()) {
                    genresDao.deleteGenresByType(genreType)
                    genresDao.insertGenres(genres)
                }
            } catch (e: Exception) {
                // Handle API errors (e.g., log or fallback)
            }
        }

        emitAll(genresDao.getGenresByType(genreType))
    }.catch { emit(emptyList()) }
}
