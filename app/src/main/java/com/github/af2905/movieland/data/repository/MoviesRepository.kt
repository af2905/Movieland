package com.github.af2905.movieland.data.repository

import com.github.af2905.movieland.data.api.MoviesApi
import com.github.af2905.movieland.data.database.dao.MovieDao
import com.github.af2905.movieland.data.database.dao.MovieResponseDao
import com.github.af2905.movieland.data.database.entity.MovieType
import com.github.af2905.movieland.data.database.entity.ResponseWithMovies
import com.github.af2905.movieland.data.dto.MoviesResponseDto
import com.github.af2905.movieland.data.mapper.MovieDetailsDtoToEntityMapper
import com.github.af2905.movieland.data.mapper.MovieDtoToEntityListMapper
import com.github.af2905.movieland.data.mapper.MoviesResponseDtoToEntityMapper
import com.github.af2905.movieland.domain.repository.IMoviesRepository
import com.github.af2905.movieland.helper.extension.isNullOrEmpty
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesApi: MoviesApi,
    private val movieDtoMapper: MovieDtoToEntityListMapper,
    private val responseDtoMapper: MoviesResponseDtoToEntityMapper,
    private val movieDetailsDtoMapper: MovieDetailsDtoToEntityMapper,
    private val movieDao: MovieDao,
    private val movieResponseDao: MovieResponseDao
) : IMoviesRepository {

    override suspend fun getNowPlayingMovies(
        language: String?, page: Int?, region: String?, forced: Boolean
    ): ResponseWithMovies =
        loadMovies(MovieType.NOW_PLAYING.name, language, page, region = region, forced = forced)

    override suspend fun getPopularMovies(
        language: String?, page: Int?, region: String?, forced: Boolean
    ): ResponseWithMovies =
        loadMovies(MovieType.POPULAR.name, language, page, region = region, forced = forced)

    override suspend fun getTopRatedMovies(
        language: String?, page: Int?, region: String?, forced: Boolean
    ): ResponseWithMovies =
        loadMovies(MovieType.TOP_RATED.name, language, page, region = region, forced = forced)

    override suspend fun getUpcomingMovies(
        language: String?, page: Int?, region: String?, forced: Boolean
    ): ResponseWithMovies =
        loadMovies(MovieType.UPCOMING.name, language, page, region = region, forced = forced)

    override suspend fun getRecommendedMovies(
        movieId: Int, language: String?, page: Int?
    ) = loadMovies(MovieType.RECOMMENDED.name, movieId = movieId, language = language, page = page)

    override suspend fun getSimilarMovies(
        movieId: Int, language: String?, page: Int?
    ): MoviesResponseDto {
        return moviesApi.getSimilarMovies(movieId, language, page)
    }

    override suspend fun getMovieActors(movieId: Int, language: String?) =
        moviesApi.getMovieActors(movieId = movieId, language = language)

    override suspend fun getMovieDetails(movieId: Int, language: String?) =
        movieDetailsDtoMapper.map(moviesApi.getMovieDetails(movieId = movieId, language = language))

    private suspend fun loadMovies(
        type: String,
        language: String?,
        page: Int?,
        region: String? = null,
        movieId: Int? = null,
        forced: Boolean = false
    ): ResponseWithMovies {

        val count = movieResponseDao.getByType(type)?.movies?.size

        val timeStamp =
            count?.let { movieResponseDao.getByType(type)!!.moviesResponseEntity.timeStamp }

        val currentTime = Calendar.getInstance().timeInMillis

        val timeDiff = timeStamp?.let {
            periodOfTimeInHours(timeStamp = it, currentTime = currentTime)
        }

        val needToUpdate = timeDiff?.let { it > DEFAULT_UPDATE_MOVIE_HOURS }

        if (count.isNullOrEmpty() || forced || needToUpdate == true) {
            val dto = when (type) {
                MovieType.NOW_PLAYING.name -> moviesApi.getNowPlayingMovies(language, page, region)
                MovieType.POPULAR.name -> moviesApi.getPopularMovies(language, page, region)
                MovieType.TOP_RATED.name -> moviesApi.getTopRatedMovies(language, page, region)
                MovieType.UPCOMING.name -> moviesApi.getUpcomingMovies(language, page, region)

                else -> moviesApi.getRecommendedMovies(movieId!!, language, page)
            }

            val response = responseDtoMapper.map(dto, type, currentTime)
            movieResponseDao.save(response)
            movieDtoMapper.map(dto.movies, type, currentTime).map { movieDao.save(it) }
        }

        return movieResponseDao.getByType(type)!!
    }

    private fun periodOfTimeInMin(timeStamp: Long, currentTime: Long) =
        TimeUnit.MILLISECONDS.toMinutes(currentTime - timeStamp)

    private fun periodOfTimeInHours(timeStamp: Long, currentTime: Long) =
        TimeUnit.MILLISECONDS.toHours(currentTime - timeStamp)

    companion object {
        const val DEFAULT_UPDATE_MOVIE_HOURS = 24
    }

}