package com.github.af2905.movieland.detail.usecase.tvshow

import com.github.af2905.movieland.core.common.model.item.TvShowDetailItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.TvShowDetailMapper
import com.github.af2905.movieland.core.repository.TvShowsRepository
import javax.inject.Inject

class RemoveFromLikedTvShow @Inject constructor(
    private val tvShowsRepository: TvShowsRepository,
    private val mapper: TvShowDetailMapper
) : CoroutineUseCase<UnlikedTvShowDetailParams, Boolean>() {

    override suspend fun execute(params: UnlikedTvShowDetailParams): Boolean {
        return tvShowsRepository.removeTvShowDetail(tvShowDetail = mapper.map(params.tvShowDetail))
    }
}

data class UnlikedTvShowDetailParams(val tvShowDetail: TvShowDetailItem)