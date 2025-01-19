package com.github.af2905.movieland.core.repository.impl

import com.github.af2905.movieland.core.data.api.GenresApi
import com.github.af2905.movieland.core.data.database.dao.GenresDao
import com.github.af2905.movieland.core.data.database.entity.Genres
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
        private const val CACHE_VALIDITY_PERIOD = 24 * 60 * 60 * 1000 // 24 hours in milliseconds
    }

    override fun getGenres(language: String?): Flow<List<Genres>> = flow {
        // Check cached genres
        val cachedGenres = genresDao.getGenres().firstOrNull()
        val lastUpdated = cachedGenres?.firstOrNull()?.timeStamp ?: 0L
        val isCacheStale = System.currentTimeMillis() - lastUpdated > CACHE_VALIDITY_PERIOD

        // Fetch fresh data if cache is stale or not available
        if (cachedGenres.isNullOrEmpty() || isCacheStale) {
            try {
                val response = genresApi.getGenres(language = language)
                val genres = response.genres.map { genreDto ->
                    Genres(
                        id = genreDto.id,
                        name = genreDto.name,
                        timeStamp = System.currentTimeMillis()
                    )
                }

                if (genres.isNotEmpty()) {
                    genresDao.deleteAllGenres()
                    genresDao.insertGenres(genres)
                }
            } catch (e: Exception) {
                emit(emptyList()) // Emit empty list if API fails and no cache is available
                return@flow
            }
        }

        // Emit cached genres (updated or existing)
        emitAll(genresDao.getGenres())
    }.catch {
        emit(emptyList()) // Fallback to empty list in case of any errors
    }
}
