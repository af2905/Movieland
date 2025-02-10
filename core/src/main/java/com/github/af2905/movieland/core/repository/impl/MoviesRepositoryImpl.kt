package com.github.af2905.movieland.core.repository.impl

import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.helper.StringProvider
import com.github.af2905.movieland.core.common.network.ResultWrapper
import com.github.af2905.movieland.core.data.api.MoviesApi
import com.github.af2905.movieland.core.data.database.dao.MovieDao
import com.github.af2905.movieland.core.data.database.entity.CreditsCast
import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.database.entity.MovieDetail
import com.github.af2905.movieland.core.data.database.entity.MovieType
import com.github.af2905.movieland.core.data.database.entity.Video
import com.github.af2905.movieland.core.data.dto.movie.MovieExternalIds
import com.github.af2905.movieland.core.data.mapper.CreditsCastMapper
import com.github.af2905.movieland.core.data.mapper.MovieDetailMapper
import com.github.af2905.movieland.core.data.mapper.MovieMapper
import com.github.af2905.movieland.core.data.mapper.VideoMapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val moviesApi: MoviesApi,
    private val movieMapper: MovieMapper,
    private val creditsMapper: CreditsCastMapper,
    private val movieDetailMapper: MovieDetailMapper,
    private val videoMapper: VideoMapper,
    private val stringProvider: StringProvider
) : MoviesRepository {

    override fun getMovies(
        movieType: MovieType,
        language: String?,
        page: Int?
    ): Flow<ResultWrapper<List<Movie>>> = flow {
        emit(ResultWrapper.Loading)

        val cachedMovies = movieDao.getMoviesByType(movieType).firstOrNull()
        val lastUpdated = cachedMovies?.firstOrNull()?.timeStamp ?: 0L
        val isCacheStale = System.currentTimeMillis() - lastUpdated > TimeUnit.HOURS.toMillis(8)

        if (cachedMovies.isNullOrEmpty() || isCacheStale) {
            val response = when (movieType) {
                MovieType.NOW_PLAYING -> moviesApi.getNowPlayingMovies(language, page)
                MovieType.POPULAR -> moviesApi.getPopularMovies(language, page)
                MovieType.TOP_RATED -> moviesApi.getTopRatedMovies(language, page)
                MovieType.UPCOMING -> moviesApi.getUpcomingMovies(language, page)
                else -> null
            }

            val movies = response?.movies?.let {
                movieMapper.map(it).map { movie ->
                    movie.copy(movieType = movieType, timeStamp = System.currentTimeMillis())
                }
                    .filter { movie -> !movie.backdropPath.isNullOrEmpty() && !movie.posterPath.isNullOrEmpty() }
            }

            if (movies != null) {
                cacheMovies(movieType, movies)
            }
        }
        val result = movieDao.getMoviesByType(movieType).firstOrNull().orEmpty()
        emit(ResultWrapper.Success(result))
    }.catch { e ->
        val errorMessage = when (e) {
            is IOException -> stringProvider.getString(R.string.error_network)
            is HttpException -> stringProvider.getString(
                R.string.error_server,
                e.code(),
                e.message()
            )

            else -> stringProvider.getString(R.string.error_unexpected)
        }
        emit(ResultWrapper.Error(errorMessage, e))
    }

    private suspend fun cacheMovies(movieType: MovieType, movies: List<Movie>) {
        movieDao.deleteMoviesByType(movieType)
        movieDao.insertMovies(movies)
    }

    override suspend fun getMovieDetails(
        movieId: Int,
        language: String?
    ): ResultWrapper<MovieDetail> {
        return try {
            val movieDetailDto = moviesApi.getMovieDetails(movieId, language)
            val movieDetail = movieDetailMapper.map(movieDetailDto)
            ResultWrapper.Success(movieDetail)
        } catch (e: HttpException) {
            ResultWrapper.Error(
                stringProvider.getString(
                    R.string.error_server,
                    e.code(),
                    e.message()
                ), e
            )
        } catch (e: IOException) {
            ResultWrapper.Error(stringProvider.getString(R.string.error_network), e)
        } catch (e: Exception) {
            ResultWrapper.Error(stringProvider.getString(R.string.error_unexpected), e)
        }
    }

    override fun getRecommendedMovies(
        movieId: Int,
        language: String?,
        page: Int?
    ): Flow<ResultWrapper<List<Movie>>> = flow {
        emit(ResultWrapper.Loading)
        try {
            val response = moviesApi.getRecommendedMovies(movieId, language, page)
            val movies = response.movies.let { movieMapper.map(it) }
                .filter { movie -> !movie.backdropPath.isNullOrEmpty() && !movie.posterPath.isNullOrEmpty() }
            emit(ResultWrapper.Success(movies))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(stringProvider.getString(R.string.error_unexpected), e))
        }
    }

    override fun getSimilarMovies(
        movieId: Int,
        language: String?,
        page: Int?
    ): Flow<ResultWrapper<List<Movie>>> = flow {
        emit(ResultWrapper.Loading)
        try {
            val response = moviesApi.getSimilarMovies(movieId, language, page)
            val movies = response.movies.let { movieMapper.map(it) }
                .filter { movie -> !movie.backdropPath.isNullOrEmpty() && !movie.posterPath.isNullOrEmpty() }
            emit(ResultWrapper.Success(movies))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(stringProvider.getString(R.string.error_unexpected), e))
        }
    }

    override fun getMovieCredits(
        movieId: Int,
        language: String?
    ): Flow<ResultWrapper<List<CreditsCast>>> = flow {
        emit(ResultWrapper.Loading)
        try {
            val response = moviesApi.getMovieCredits(movieId, language)
            val cast = response.cast?.map {
                creditsMapper.map(it, movieId)
            }?.filter { cast -> !cast.profilePath.isNullOrEmpty() }
            emit(ResultWrapper.Success(cast ?: emptyList()))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(stringProvider.getString(R.string.error_unexpected), e))
        }
    }

    override fun getMovieVideos(movieId: Int, language: String?): Flow<ResultWrapper<List<Video>>> =
        flow {
            emit(ResultWrapper.Loading)
            try {
                val response = moviesApi.getMovieVideos(movieId, language)
                val videos = response.results.map { videoMapper.map(it) }
                emit(ResultWrapper.Success(videos))
            } catch (e: Exception) {
                emit(ResultWrapper.Error(stringProvider.getString(R.string.error_unexpected), e))
            }
        }

    override suspend fun getMovieExternalIds(
        movieId: Int,
        language: String?
    ): ResultWrapper<MovieExternalIds?> {
        return try {
            val movieExternalIds = moviesApi.getMovieExternalIds(movieId, language)
            ResultWrapper.Success(movieExternalIds)
        } catch (e: Exception) {
            ResultWrapper.Success(null)
        }
    }
}