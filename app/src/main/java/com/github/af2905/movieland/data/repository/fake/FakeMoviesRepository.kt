package com.github.af2905.movieland.data.repository.fake

import com.github.af2905.movieland.data.database.entity.MovieDetailsEntity
import com.github.af2905.movieland.data.database.entity.ResponseWithMovies
import com.github.af2905.movieland.data.dto.MovieActorsResponseDto
import com.github.af2905.movieland.data.dto.MoviesResponseDto
import com.github.af2905.movieland.domain.repository.IMoviesRepository
import javax.inject.Inject

class FakeMoviesRepository @Inject constructor(private val fakeMoviesFactory: FakeMoviesFactory) :
    IMoviesRepository {
    override suspend fun getNowPlayingMovies(
        language: String?,
        page: Int?,
        region: String?,
        forced: Boolean
    ): ResponseWithMovies {
        return fakeMoviesFactory.createFakeResponseWithMovies()
    }

    override suspend fun getPopularMovies(
        language: String?,
        page: Int?,
        region: String?,
        forced: Boolean
    ): ResponseWithMovies {
        return fakeMoviesFactory.createFakeResponseWithMovies()
    }

    override suspend fun getTopRatedMovies(
        language: String?,
        page: Int?,
        region: String?,
        forced: Boolean
    ): ResponseWithMovies {
        return fakeMoviesFactory.createFakeResponseWithMovies()
    }

    override suspend fun getUpcomingMovies(
        language: String?,
        page: Int?,
        region: String?,
        forced: Boolean
    ): ResponseWithMovies {
        return fakeMoviesFactory.createFakeResponseWithMovies()
    }

    override suspend fun getMovieDetails(movieId: Int, language: String?): MovieDetailsEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getRecommendedMovies(
        movieId: Int,
        language: String?,
        page: Int?
    ): ResponseWithMovies {
        return fakeMoviesFactory.createFakeResponseWithMovies()
    }

    override suspend fun getSimilarMovies(
        movieId: Int,
        language: String?,
        page: Int?
    ): MoviesResponseDto {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieActors(
        movieId: Int,
        language: String?
    ): MovieActorsResponseDto {
        TODO("Not yet implemented")
    }
}
