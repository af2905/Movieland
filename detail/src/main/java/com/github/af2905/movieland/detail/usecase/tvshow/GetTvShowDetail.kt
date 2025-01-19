package com.github.af2905.movieland.detail.usecase.tvshow

import com.github.af2905.movieland.core.common.model.item.TvShowDetailItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.TvShowDetailMapper
import com.github.af2905.movieland.core.repository.TvShowsRepository
import javax.inject.Inject

class GetTvShowDetail @Inject constructor(
    private val tvShowsRepository: TvShowsRepository,
    private val mapper: TvShowDetailMapper
) : CoroutineUseCase<TvShowDetailParams, TvShowDetailItem?>() {

    override suspend fun execute(params: TvShowDetailParams): TvShowDetailItem? {
        /*return mapper.map(
            tvShowsRepository.getTvShowDetail(
                tvShowId = params.tvShowId,
                language = params.language
            )
        )*/
        return null
    }
}

data class TvShowDetailParams(
    val tvShowId: Int,
    val language: String? = null
)