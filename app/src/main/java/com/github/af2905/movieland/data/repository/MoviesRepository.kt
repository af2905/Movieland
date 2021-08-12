package com.github.af2905.movieland.data.repository

import com.github.af2905.movieland.data.api.MoviesApi
import com.github.af2905.movieland.data.database.dao.MovieDao
import com.github.af2905.movieland.data.database.dao.MovieResponseDao
import com.github.af2905.movieland.data.database.entity.MovieType
import com.github.af2905.movieland.data.mapper.MovieDtoToEntityListMapper
import com.github.af2905.movieland.data.mapper.MoviesResponseDtoToEntityMapper
import com.github.af2905.movieland.data.mapper.MoviesResponseEntityToUIMapper
import com.github.af2905.movieland.domain.repository.IMoviesRepository
import com.github.af2905.movieland.presentation.model.item.MoviesResponse
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesApi: MoviesApi,
    private val movieDtoMapper: MovieDtoToEntityListMapper,
    private val responseDtoMapper: MoviesResponseDtoToEntityMapper,
    private val responseEntityMapper: MoviesResponseEntityToUIMapper,
    private val movieDao: MovieDao,
    private val movieResponseDao: MovieResponseDao
) : IMoviesRepository {

    override suspend fun getNowPlayingMovies(
        language: String?, page: Int?, region: String?
    ): MoviesResponse = loadMovies(MovieType.NOW_PLAYING.name, language, page, region = region)

    override suspend fun getPopularMovies(
        language: String?, page: Int?, region: String?
    ): MoviesResponse = loadMovies(MovieType.POPULAR.name, language, page, region = region)

    override suspend fun getTopRatedMovies(
        language: String?, page: Int?, region: String?
    ): MoviesResponse = loadMovies(MovieType.TOP_RATED.name, language, page, region = region)

    override suspend fun getUpcomingMovies(
        language: String?, page: Int?, region: String?
    ): MoviesResponse = loadMovies(MovieType.UPCOMING.name, language, page, region = region)

    override suspend fun getRecommendedMovies(
        movieId: Int, language: String?, page: Int?
    ) = loadMovies(MovieType.RECOMMENDED.name, movieId = movieId, language = language, page = page)

    override suspend fun getSimilarMovies(
        movieId: Int, language: String?, page: Int?
    ) = loadMovies(MovieType.SIMILAR.name, movieId = movieId, language = language, page = page)

    override suspend fun getMovieDetails(movieId: Int, language: String?) =
        moviesApi.getMovieDetails(movieId = movieId, language = language)

    private fun checkCountNullOrEmpty(count: Int?) = (count == null || count == 0)

    private suspend fun loadMovies(
        type: String, language: String?, page: Int?, region: String? = null, movieId: Int? = null
    ): MoviesResponse {
        val count = movieResponseDao.getTotalResultsByType(type)
        if (checkCountNullOrEmpty(count)) {
            val dto = when (type) {
                MovieType.NOW_PLAYING.name -> moviesApi.getNowPlayingMovies(language, page, region)
                MovieType.POPULAR.name -> moviesApi.getPopularMovies(language, page, region)
                MovieType.TOP_RATED.name -> moviesApi.getPopularMovies(language, page, region)
                MovieType.UPCOMING.name -> moviesApi.getPopularMovies(language, page, region)
                MovieType.SIMILAR.name -> moviesApi.getSimilarMovies(movieId!!, language, page)
                else -> moviesApi.getRecommendedMovies(movieId!!, language, page)
            }
            val response = responseDtoMapper.map(dto, type)
            movieResponseDao.save(response)
            movieDtoMapper.map(dto.movies, type).map { movieDao.save(it) }
        }
        return responseEntityMapper.map(movieResponseDao.getByType(type)!!)
    }
}