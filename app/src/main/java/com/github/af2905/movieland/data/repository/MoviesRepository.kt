package com.github.af2905.movieland.data.repository

import com.github.af2905.movieland.data.api.MoviesApi
import com.github.af2905.movieland.data.database.dao.MovieDao
import com.github.af2905.movieland.data.database.dao.MovieResponseDao
import com.github.af2905.movieland.data.database.entity.MovieType
import com.github.af2905.movieland.data.mapper.MovieDtoToEntityListMapper
import com.github.af2905.movieland.data.mapper.MoviesResponseDtoToEntityMapper
import com.github.af2905.movieland.data.mapper.MoviesResponseEntityMapper
import com.github.af2905.movieland.domain.repository.IMoviesRepository
import com.github.af2905.movieland.presentation.model.item.MoviesResponse
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesApi: MoviesApi,
    private val movieDtoMapper: MovieDtoToEntityListMapper,
    private val responseDtoMapper: MoviesResponseDtoToEntityMapper,
    private val responseEntityMapper: MoviesResponseEntityMapper,
    private val movieDao: MovieDao,
    private val movieResponseDao: MovieResponseDao
) : IMoviesRepository {

    override suspend fun getNowPlayingMovies(
        language: String?, page: Int?, region: String?
    ) = moviesApi.getNowPlayingMovies(language = language, page = page, region = region)

    override suspend fun getPopularMovies(
        language: String?, page: Int?, region: String?
    ): MoviesResponse {
        val countNullOrEmpty =
            checkCountNullOrEmpty(movieResponseDao.getTotalResultsByType(MovieType.POPULAR.name))

        if (countNullOrEmpty) {
            val dto = moviesApi.getPopularMovies(language = language, page = page, region = region)
            val response = responseDtoMapper.map(dto, MovieType.POPULAR.name)
            movieResponseDao.save(response)

            val movies = movieDtoMapper.map(dto.movies, MovieType.POPULAR.name)
            movies.map { movieDao.insert(it) }
        }
        return responseEntityMapper.map(movieResponseDao.getByType(MovieType.POPULAR.name)!!)
    }

    override suspend fun getTopRatedMovies(
        language: String?, page: Int?, region: String?
    ) = moviesApi.getTopRatedMovies(language = language, page = page, region = region)

    override suspend fun getUpcomingMovies(
        language: String?, page: Int?, region: String?
    ) = moviesApi.getUpcomingMovies(language = language, page = page, region = region)

    override suspend fun getMovieDetails(movieId: Int, language: String?) =
        moviesApi.getMovieDetails(movieId = movieId, language = language)

    override suspend fun getRecommendedMovies(
        movieId: Int, language: String?, page: Int?
    ) = moviesApi.getRecommendedMovies(movieId, language, page)

    override suspend fun getSimilarMovies(
        movieId: Int, language: String?, page: Int?
    ) = moviesApi.getSimilarMovies(movieId, language, page)

    private fun checkCountNullOrEmpty(count: Int?) = (count == null || count == 0)

}