package com.github.af2905.movieland.core.repository.impl

import com.github.af2905.movieland.core.data.api.TvShowsApi
import com.github.af2905.movieland.core.data.database.dao.TvShowDao
import com.github.af2905.movieland.core.data.database.dao.TvShowDetailDao
import com.github.af2905.movieland.core.data.database.entity.TvShow
import com.github.af2905.movieland.core.data.database.entity.TvShowType
import com.github.af2905.movieland.core.data.mapper.TvShowMapper
import com.github.af2905.movieland.core.repository.TvShowsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.helper.StringProvider
import com.github.af2905.movieland.core.common.network.ResultWrapper
import com.github.af2905.movieland.core.data.database.entity.CreditsCast
import com.github.af2905.movieland.core.data.database.entity.TvShowDetail
import com.github.af2905.movieland.core.data.database.entity.Video
import com.github.af2905.movieland.core.data.dto.movie.ExternalIds
import com.github.af2905.movieland.core.data.mapper.CreditsCastMapper
import com.github.af2905.movieland.core.data.mapper.TvShowDetailMapper
import com.github.af2905.movieland.core.data.mapper.VideoMapper
import com.github.af2905.movieland.core.pager.tv_shows.SimilarOrRecommendedTvShowsPagingSource
import com.github.af2905.movieland.core.pager.tv_shows.TvShowsPagingSource
import kotlinx.coroutines.flow.catch
import retrofit2.HttpException
import java.io.IOException

class TvShowsRepositoryImpl @Inject constructor(
    private val tvShowsApi: TvShowsApi,
    private val tvShowDao: TvShowDao,
    private val tvShowDetailDao: TvShowDetailDao,
    private val tvShowMapper: TvShowMapper,
    private val tvShowDetailMapper: TvShowDetailMapper,
    private val creditsMapper: CreditsCastMapper,
    private val videoMapper: VideoMapper,
    private val stringProvider: StringProvider
) : TvShowsRepository {

    override fun getCachedTvShows(
        tvShowType: TvShowType,
        language: String?,
        page: Int?
    ): Flow<ResultWrapper<List<TvShow>>> = flow {
        emit(ResultWrapper.Loading)

        val cachedTvShows = tvShowDao.getTvShowsByType(tvShowType).firstOrNull()
        val lastUpdated = cachedTvShows?.firstOrNull()?.timeStamp ?: 0L
        val isCacheStale = System.currentTimeMillis() - lastUpdated > TimeUnit.HOURS.toMillis(4)

        if (cachedTvShows.isNullOrEmpty() || isCacheStale) {
            val response = when (tvShowType) {
                TvShowType.POPULAR -> tvShowsApi.getPopularTvShows(language, page)
                TvShowType.TOP_RATED -> tvShowsApi.getTopRatedTvShows(language, page)
                TvShowType.ON_THE_AIR -> tvShowsApi.getOnTheAirTvShows(language, page)
                TvShowType.AIRING_TODAY -> tvShowsApi.getAiringTodayTvShows(language, page)
                else -> null
            }

            val tvShows = response?.tvShows?.let {
                tvShowMapper.map(it).map { tvShow ->
                    tvShow.copy(tvShowType = tvShowType, timeStamp = System.currentTimeMillis())
                }.filter { tvShow -> !tvShow.backdropPath.isNullOrEmpty() && !tvShow.posterPath.isNullOrEmpty() }
            }

            if (tvShows != null) {
                cacheTvShows(tvShowType, tvShows)
            }
        }

        val result = tvShowDao.getTvShowsByType(tvShowType).firstOrNull().orEmpty()
        emit(ResultWrapper.Success(result))
    }.catch { e ->
        emit(handleError(e))
    }

    override fun getTvShows(
        tvShowType: TvShowType,
        language: String?,
        page: Int?
    ): Flow<ResultWrapper<List<TvShow>>> = flow {
        try {
            val response = when (tvShowType) {
                TvShowType.POPULAR -> tvShowsApi.getPopularTvShows(language, page)
                TvShowType.TOP_RATED -> tvShowsApi.getTopRatedTvShows(language, page)
                TvShowType.ON_THE_AIR -> tvShowsApi.getOnTheAirTvShows(language, page)
                TvShowType.AIRING_TODAY -> tvShowsApi.getAiringTodayTvShows(language, page)
                else -> null
            }

            if (response == null) throw Exception("Null response from API")

            val tvShows = response.tvShows.let {
                tvShowMapper.map(it).map { tvShow ->
                    tvShow.copy(tvShowType = tvShowType, timeStamp = System.currentTimeMillis())
                }.filter { tvShow -> !tvShow.backdropPath.isNullOrEmpty() && !tvShow.posterPath.isNullOrEmpty() }
            }

            emit(ResultWrapper.Success(tvShows))
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getTvShowsPaginated(
        tvShowType: TvShowType,
        language: String?
    ): Flow<PagingData<TvShow>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { TvShowsPagingSource(this, tvShowType, language) }
        ).flow
    }

    override suspend fun getTvShowDetails(
        tvShowId: Int,
        language: String?
    ): ResultWrapper<TvShowDetail> {
        return try {
            val tvShowDetailDto = tvShowsApi.getTvShowDetail(tvShowId, language)
            val tvShowDetail = tvShowDetailMapper.map(tvShowDetailDto)
            ResultWrapper.Success(tvShowDetail)
        } catch (e: Exception) {
            handleError(e)
        }
    }

    override fun getRecommendedTvShows(
        tvShowId: Int,
        language: String?,
        page: Int?
    ): Flow<ResultWrapper<List<TvShow>>> = flow {
        emit(ResultWrapper.Loading)

        try {
            val response = tvShowsApi.getRecommendedTvShows(tvShowId, language, page)
            val tvShows = response.tvShows.let { tvShowMapper.map(it) }
                .filter { tvShow -> !tvShow.backdropPath.isNullOrEmpty() && !tvShow.posterPath.isNullOrEmpty() }

            emit(ResultWrapper.Success(tvShows))
        } catch (e: Exception) {
            emit(handleError(e))
        }
    }

    override fun getSimilarTvShows(
        tvShowId: Int,
        language: String?,
        page: Int?
    ): Flow<ResultWrapper<List<TvShow>>> = flow {
        emit(ResultWrapper.Loading)

        try {
            val response = tvShowsApi.getSimilarTvShows(tvShowId, language, page)
            val tvShows = response.tvShows.let { tvShowMapper.map(it) }
                .filter { tvShow -> !tvShow.backdropPath.isNullOrEmpty() && !tvShow.posterPath.isNullOrEmpty() }

            emit(ResultWrapper.Success(tvShows))
        } catch (e: Exception) {
            emit(handleError(e))
        }
    }

    override fun getSimilarOrRecommendedPaginated(
        tvShowId: Int,
        tvShowType: TvShowType,
        language: String?
    ): Flow<PagingData<TvShow>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 40
            ),
            pagingSourceFactory = {
                SimilarOrRecommendedTvShowsPagingSource(
                    tvShowsRepository = this,
                    tvShowId = tvShowId,
                    tvShowType = tvShowType,
                    language = language
                )
            }
        ).flow
    }

    override fun getTvShowCredits(
        tvShowId: Int,
        language: String?
    ): Flow<ResultWrapper<List<CreditsCast>>> = flow {
        emit(ResultWrapper.Loading)
        try {
            val response = tvShowsApi.getTvShowCredits(tvShowId, language)
            val cast = response.cast?.map {
                creditsMapper.map(it, tvShowId)
            }?.filter { cast -> !cast.profilePath.isNullOrEmpty() }

            emit(ResultWrapper.Success(cast ?: emptyList()))
        } catch (e: Exception) {
            emit(handleError(e))
        }
    }

    override fun getTvShowVideos(tvShowId: Int, language: String?): Flow<ResultWrapper<List<Video>>> =
        flow {
            emit(ResultWrapper.Loading)
            try {
                val response = tvShowsApi.getTvShowVideos(tvShowId, language)
                val videos = response.results.map { videoMapper.map(it) }
                emit(ResultWrapper.Success(videos))
            } catch (e: Exception) {
                emit(handleError(e))
            }
        }

    override suspend fun getTvShowExternalIds(
        tvShowId: Int
    ): ResultWrapper<ExternalIds?> {
        return try {
            val externalIds = tvShowsApi.getTvShowExternalIds(tvShowId)
            ResultWrapper.Success(externalIds)
        } catch (e: Exception) {
            ResultWrapper.Success(null)
        }
    }

    private suspend fun cacheTvShows(tvShowType: TvShowType, tvShows: List<TvShow>) {
        tvShowDao.deleteTvShowsByType(tvShowType)
        tvShowDao.insertTvShows(tvShows)
    }

    private fun handleError(e: Throwable): ResultWrapper.Error {
        val errorMessage = when (e) {
            is IOException -> stringProvider.getString(R.string.error_network)
            is HttpException -> stringProvider.getString(R.string.error_server, e.code(), e.message())
            else -> stringProvider.getString(R.string.error_unexpected)
        }
        return ResultWrapper.Error(errorMessage, e)
    }

    override suspend fun saveTvShow(tvShow: TvShowDetail) {
        tvShowDetailDao.save(tvShow)
    }

    override suspend fun removeTvShow(tvShow: TvShowDetail) {
        tvShowDetailDao.delete(tvShow)
    }

    override suspend fun toggleTvShowLike(tvShow: TvShowDetail) {
        tvShowDetailDao.toggleTvShowLike(tvShow)
    }

    override suspend fun isTvShowSaved(id: Int): Boolean {
        return tvShowDetailDao.isTvShowSaved(id)
    }
}