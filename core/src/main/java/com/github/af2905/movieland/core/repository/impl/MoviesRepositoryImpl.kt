package com.github.af2905.movieland.core.repository.impl

import com.github.af2905.movieland.core.data.api.MoviesApi
import com.github.af2905.movieland.core.data.database.dao.MovieDao
import com.github.af2905.movieland.core.data.database.dao.MovieDetailDao
import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.database.entity.MovieDetail
import com.github.af2905.movieland.core.data.database.entity.MovieType
import com.github.af2905.movieland.core.data.datastore.ResourceDatastore
import com.github.af2905.movieland.core.data.dto.movie.MovieCreditsCastDto
import com.github.af2905.movieland.core.data.dto.movie.MovieDetailDto
import com.github.af2905.movieland.core.data.mapper.MovieMapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.util.extension.isNullOrEmpty
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val DEFAULT_UPDATE_MOVIE_HOURS = 24L

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    private val movieMapper: MovieMapper,
    private val movieDao: MovieDao,
    private val movieDetailDao: MovieDetailDao,
    private val resourceDatastore: ResourceDatastore
) : MoviesRepository {

    override suspend fun getNowPlayingMovies(
        language: String?, page: Int?, region: String?, forceUpdate: Boolean
    ): List<Movie> = loadMovies(
        type = MovieType.NOW_PLAYING.name,
        language = language ?: resourceDatastore.getLanguage(),
        page = page,
        region = region,
        forceUpdate = forceUpdate
    )

    override suspend fun getPopularMovies(
        language: String?, page: Int?, region: String?, forceUpdate: Boolean
    ): List<Movie> = loadMovies(
        MovieType.POPULAR.name,
        language = language ?: resourceDatastore.getLanguage(),
        page = page,
        region = region,
        forceUpdate = forceUpdate
    )

    override suspend fun getTopRatedMovies(
        language: String?, page: Int?, region: String?, forceUpdate: Boolean
    ): List<Movie> = loadMovies(
        MovieType.TOP_RATED.name,
        language = language ?: resourceDatastore.getLanguage(),
        page = page,
        region = region,
        forceUpdate = forceUpdate
    )

    override suspend fun getUpcomingMovies(
        language: String?, page: Int?, region: String?, forceUpdate: Boolean
    ): List<Movie> = loadMovies(
        MovieType.UPCOMING.name,
        language = language ?: resourceDatastore.getLanguage(),
        page = page,
        region = region,
        forceUpdate = forceUpdate
    )

    override suspend fun getRecommendedMovies(
        movieId: Int, language: String?, page: Int?
    ): List<Movie> {
        val response = moviesApi.getRecommendedMovies(
            movieId = movieId,
            language = language ?: resourceDatastore.getLanguage(),
            page = page
        )
        return movieMapper.map(response.movies, MovieType.RECOMMENDED.name, 0)
    }

    override suspend fun getSimilarMovies(
        movieId: Int, language: String?, page: Int?
    ): List<Movie> {
        val response = moviesApi.getSimilarMovies(
            movieId = movieId,
            language = language ?: resourceDatastore.getLanguage(),
            page = page
        )
        return movieMapper.map(response.movies, MovieType.SIMILAR.name, 0)
    }

    override suspend fun getMovieCredits(
        movieId: Int,
        language: String?
    ): List<MovieCreditsCastDto> = moviesApi.getMovieCredits(
        movieId = movieId,
        language = language ?: resourceDatastore.getLanguage()
    ).cast.orEmpty()

    override suspend fun saveMovieDetail(movieDetail: MovieDetail): Boolean {
        return movieDetailDao.save(movieDetail)?.let { true } ?: false
    }

    override suspend fun removeMovieDetail(movieDetail: MovieDetail): Boolean {
        return movieDetailDao.delete(movieDetail)?.let { true } ?: false
    }

    override suspend fun getMovieDetailById(id: Int): MovieDetail? {
        return movieDetailDao.getById(id)
    }

    override suspend fun getAllSavedMovieDetail(): List<MovieDetail> {
        return movieDetailDao.getAll() ?: emptyList()
    }

    override suspend fun getMovieDetail(movieId: Int, language: String?): MovieDetailDto =
        moviesApi.getMovieDetails(
            movieId = movieId,
            language = language ?: resourceDatastore.getLanguage()
        )

    override suspend fun getCachedMoviesByType(type: MovieType): List<Movie> =
        movieDao.getByType(type.name).orEmpty()

    private suspend fun loadMovies(
        type: String,
        language: String?,
        page: Int?,
        region: String? = null,
        movieId: Int? = null,
        forceUpdate: Boolean
    ): List<Movie> {

        val count = movieDao.getCountByType(type)

        val timeStamp = count?.let { movieDao.getTimeStampByType(type) }

        val currentTime = Calendar.getInstance().timeInMillis

        val timeDiff = timeStamp?.let {
            periodOfTimeInHours(
                timeStamp = it,
                currentTime = currentTime
            )
        }

        val needToUpdate = timeDiff?.let {
            it > TimeUnit.HOURS.toMillis(DEFAULT_UPDATE_MOVIE_HOURS)
        }

        if (count.isNullOrEmpty() || needToUpdate == true || forceUpdate) {
            val dto = when (type) {
                MovieType.NOW_PLAYING.name -> moviesApi.getNowPlayingMovies(language, page, region)
                MovieType.POPULAR.name -> moviesApi.getPopularMovies(language, page, region)
                MovieType.TOP_RATED.name -> moviesApi.getTopRatedMovies(language, page, region)
                MovieType.UPCOMING.name -> moviesApi.getUpcomingMovies(language, page, region)
                MovieType.RECOMMENDED.name -> {
                    moviesApi.getRecommendedMovies(movieId!!, language, page)
                }
                else -> moviesApi.getSimilarMovies(movieId!!, language, page)
            }
            movieMapper.map(dto.movies, type, currentTime).map { movieDao.save(it) }
        }
        return movieDao.getByType(type).orEmpty()
    }

    private fun periodOfTimeInHours(timeStamp: Long, currentTime: Long) =
        TimeUnit.MILLISECONDS.toHours(currentTime - timeStamp)
}