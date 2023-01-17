package com.github.af2905.movieland.core.repository.impl

import com.github.af2905.movieland.core.data.api.TvShowsApi
import com.github.af2905.movieland.core.data.database.dao.TvShowDao
import com.github.af2905.movieland.core.data.database.dao.TvShowDetailDao
import com.github.af2905.movieland.core.data.database.entity.TvShow
import com.github.af2905.movieland.core.data.database.entity.TvShowType
import com.github.af2905.movieland.core.data.datastore.ResourceDatastore
import com.github.af2905.movieland.core.data.dto.tv.TvShowDetailDto
import com.github.af2905.movieland.core.data.dto.tv.TvShowDto
import com.github.af2905.movieland.core.data.mapper.TvShowMapper
import com.github.af2905.movieland.core.repository.TvShowsRepository
import com.github.af2905.movieland.util.extension.isNullOrEmpty
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val DEFAULT_UPDATE_TV_SHOW_HOURS = 24L

class TvShowsRepositoryImpl @Inject constructor(
    private val tvShowsApi: TvShowsApi,
    private val tvShowDao: TvShowDao,
    private val tvShowDetailDao: TvShowDetailDao,
    private val tvShowMapper: TvShowMapper,
    private val resourceDatastore: ResourceDatastore
) : TvShowsRepository {
    override suspend fun getPopularTvShows(
        language: String?,
        page: Int?,
        forceUpdate: Boolean
    ): List<TvShow> = loadTvShows(
        type = TvShowType.POPULAR,
        language = language ?: resourceDatastore.getLanguage(),
        page = page,
        forceUpdate = forceUpdate
    )

    override suspend fun getTopRatedTvShows(
        language: String?,
        page: Int?,
        forceUpdate: Boolean
    ): List<TvShow> = loadTvShows(
        type = TvShowType.TOP_RATED,
        language = language ?: resourceDatastore.getLanguage(),
        page = page,
        forceUpdate = forceUpdate
    )

    override suspend fun getTvShowDetail(tvShowId: Int, language: String?): TvShowDetailDto =
        tvShowsApi.getTvShowDetail(tvId = tvShowId, language = language)

    override suspend fun getSimilarTvShows(tvShowId: Int, language: String?): List<TvShowDto> =
        tvShowsApi.getSimilarTvShows(tvId = tvShowId, language = language).tvShows

    override suspend fun getCachedTvShowsByType(type: TvShowType): List<TvShow> =
        tvShowDao.getByType(type.name).orEmpty()

    private suspend fun loadTvShows(
        type: TvShowType,
        language: String?,
        page: Int?,
        forceUpdate: Boolean
    ): List<TvShow> {

        val count = tvShowDao.getCountByType(type.name)

        val timeStamp = count?.let { tvShowDao.getTimeStampByType(type.name) }

        val currentTime = Calendar.getInstance().timeInMillis

        val timeDiff = timeStamp?.let {
            periodOfTimeInHours(
                timeStamp = it,
                currentTime = currentTime
            )
        }

        val needToUpdate = timeDiff?.let {
            it > TimeUnit.HOURS.toMillis(DEFAULT_UPDATE_TV_SHOW_HOURS)
        }

        if (count.isNullOrEmpty() || needToUpdate == true || forceUpdate) {
            val dto = when (type) {
                TvShowType.POPULAR -> tvShowsApi.getPopularTvShows(language, page)
                TvShowType.TOP_RATED -> tvShowsApi.getTopRatedTvShows(language, page)
            }
            tvShowMapper.map(dto.tvShows, type.name, currentTime).forEach { tvShowDao.save(it) }
        }
        return tvShowDao.getByType(type.name).orEmpty()
    }

    private fun periodOfTimeInHours(timeStamp: Long, currentTime: Long) =
        TimeUnit.MILLISECONDS.toHours(currentTime - timeStamp)
}