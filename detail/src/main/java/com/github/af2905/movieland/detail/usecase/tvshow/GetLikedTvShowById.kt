package com.github.af2905.movieland.detail.usecase.tvshow

import com.github.af2905.movieland.core.common.model.item.TvShowDetailItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.TvShowDetailMapper
import com.github.af2905.movieland.core.repository.TvShowsRepository
import javax.inject.Inject

class GetLikedTvShowById @Inject constructor(
    private val tvShowsRepository: TvShowsRepository,
    private val mapper: TvShowDetailMapper
) : CoroutineUseCase<LikedTvShowByIdParams, TvShowDetailItem?>() {

    override suspend fun execute(params: LikedTvShowByIdParams): TvShowDetailItem? {
        return tvShowsRepository.getTvShowDetailById(params.tvShowId)?.let { mapper.map(it) }
    }
}

data class LikedTvShowByIdParams(val tvShowId: Int)