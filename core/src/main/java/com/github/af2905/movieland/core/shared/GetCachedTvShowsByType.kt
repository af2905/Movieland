package com.github.af2905.movieland.core.shared

import com.github.af2905.movieland.core.common.model.item.TvShowItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.database.entity.TvShowType
import com.github.af2905.movieland.core.data.mapper.TvShowMapper
import com.github.af2905.movieland.core.repository.TvShowsRepository
import javax.inject.Inject

class GetCachedTvShowsByType @Inject constructor(
    private val tvShowsRepository: TvShowsRepository,
    private val mapper: TvShowMapper
) : CoroutineUseCase<CachedTvShowsParams, List<TvShowItem>>() {
    override suspend fun execute(params: CachedTvShowsParams): List<TvShowItem> {
        val tvShows = tvShowsRepository.getCachedTvShowsByType(params.type)
        return mapper.map(tvShows)
    }
}

data class CachedTvShowsParams(val type: TvShowType)