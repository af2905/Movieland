package com.github.af2905.movieland.detail.usecase.tvshow

import com.github.af2905.movieland.core.common.model.item.TvShowDetailItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.TvShowDetailMapper
import com.github.af2905.movieland.core.repository.TvShowsRepository
import javax.inject.Inject

class SaveToLikedTvShow @Inject constructor(
    private val tvShowsRepository: TvShowsRepository,
    private val mapper: TvShowDetailMapper
) : CoroutineUseCase<LikedTvShowDetailParams, Boolean>() {

    override suspend fun execute(params: LikedTvShowDetailParams): Boolean {
        //return tvShowsRepository.saveTvShowDetail(tvShowDetail = mapper.map(params.tvShowDetail))
        return false
    }
}

data class LikedTvShowDetailParams(val tvShowDetail: TvShowDetailItem)