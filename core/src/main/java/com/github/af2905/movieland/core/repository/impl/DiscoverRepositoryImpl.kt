package com.github.af2905.movieland.core.repository.impl

import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.helper.StringProvider
import com.github.af2905.movieland.core.common.network.ResultWrapper
import com.github.af2905.movieland.core.data.api.DiscoverApi
import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.database.entity.TvShow
import com.github.af2905.movieland.core.data.mapper.MovieMapper
import com.github.af2905.movieland.core.data.mapper.TvShowMapper
import com.github.af2905.movieland.core.repository.DiscoverRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DiscoverRepositoryImpl @Inject constructor(
    private val discoverApi: DiscoverApi,
    private val movieMapper: MovieMapper,
    private val tvShowMapper: TvShowMapper,
    private val stringProvider: StringProvider
) : DiscoverRepository {

    override fun discoverMovies(
        language: String?,
        page: Int?,
        withGenres: Int?
    ): Flow<ResultWrapper<List<Movie>>> = flow {
        emit(ResultWrapper.Loading)

        try {
            val response = discoverApi.discoverMovies(language, page, withGenres)
            val movies = response.movies.let { movieMapper.map(it) }
                .filter { movie -> !movie.backdropPath.isNullOrEmpty() && !movie.posterPath.isNullOrEmpty() }

            emit(ResultWrapper.Success(movies))
        } catch (e: IOException) {
            emit(ResultWrapper.Error(stringProvider.getString(R.string.error_network), e))
        } catch (e: HttpException) {
            emit(
                ResultWrapper.Error(
                    stringProvider.getString(
                        R.string.error_server,
                        e.code(),
                        e.message()
                    ), e
                )
            )
        } catch (e: Exception) {
            emit(ResultWrapper.Error(stringProvider.getString(R.string.error_unexpected), e))
        }
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
    }.flowOn(Dispatchers.IO)

    override fun discoverTvShows(
        language: String?,
        page: Int?,
        withGenres: Int?
    ): Flow<ResultWrapper<List<TvShow>>> = flow {
        emit(ResultWrapper.Loading)

        try {
            val response = discoverApi.discoverTvShows(language, page, withGenres)
            val tvShows = response.tvShows.let { tvShowMapper.map(it) }
                .filter { tvShow -> !tvShow.backdropPath.isNullOrEmpty() && !tvShow.posterPath.isNullOrEmpty() }

            emit(ResultWrapper.Success(tvShows))
        } catch (e: IOException) {
            emit(ResultWrapper.Error(stringProvider.getString(R.string.error_network), e))
        } catch (e: HttpException) {
            emit(
                ResultWrapper.Error(
                    stringProvider.getString(
                        R.string.error_server,
                        e.code(),
                        e.message()
                    ), e
                )
            )
        } catch (e: Exception) {
            emit(ResultWrapper.Error(stringProvider.getString(R.string.error_unexpected), e))
        }
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
    }.flowOn(Dispatchers.IO)
}
