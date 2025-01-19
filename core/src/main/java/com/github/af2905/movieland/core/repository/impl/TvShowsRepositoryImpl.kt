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
import android.util.Log
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll

class TvShowsRepositoryImpl @Inject constructor(
    private val tvShowsApi: TvShowsApi,
    private val tvShowDao: TvShowDao,
    private val tvShowDetailDao: TvShowDetailDao,
    private val tvShowMapper: TvShowMapper,
) : TvShowsRepository {

    override fun getTvShows(
        tvShowType: TvShowType,
        language: String?,
        page: Int?
    ): Flow<List<TvShow>> = flow {
        val cachedTvShows = tvShowDao.getTvShowsByType(tvShowType).firstOrNull()
        val lastUpdated = cachedTvShows?.firstOrNull()?.timeStamp ?: 0L
        val isCacheStale = System.currentTimeMillis() - lastUpdated > TimeUnit.HOURS.toMillis(8)

        if (cachedTvShows.isNullOrEmpty() || isCacheStale) {
            try {
                val response = when (tvShowType) {
                    TvShowType.POPULAR -> tvShowsApi.getPopularTvShows(
                        language = language,
                        page = page
                    )

                    TvShowType.TOP_RATED -> tvShowsApi.getTopRatedTvShows(
                        language = language,
                        page = page
                    )

                    TvShowType.ON_THE_AIR -> tvShowsApi.getOnTheAirTvShows(
                        language = language,
                        page = page
                    )

                    TvShowType.AIRING_TODAY -> tvShowsApi.getAiringTodayTvShows(
                        language = language,
                        page = page
                    )

                    else -> null
                }

                Log.d("TvShowsRepositoryImpl", "API response: $response")

                val tvShows = response?.tvShows?.let {
                    tvShowMapper.map(it).map { tvShow ->
                        tvShow.copy(tvShowType = tvShowType, timeStamp = System.currentTimeMillis())
                    }
                }

                if (tvShows != null) {
                    Log.d("TvShowsRepositoryImpl", "Inserting ${tvShows.size} tv shows into the database")
                    tvShowDao.insertTvShows(tvShows)
                } else {
                    Log.d("TvShowsRepositoryImpl", "No tv shows to insert")
                }
                emit(tvShows ?: emptyList())
            } catch (e: Exception) {
                Log.e("TvShowsRepositoryImpl", "Error fetching tv shows", e)
                emit(emptyList())
            }
        } else {
            emit(cachedTvShows)
        }
    }.catch { e ->
        Log.e("TvShowsRepositoryImpl", "Error in flow", e)
        emit(emptyList())
    }
}