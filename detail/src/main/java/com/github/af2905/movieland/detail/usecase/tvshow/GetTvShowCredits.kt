package com.github.af2905.movieland.detail.usecase.tvshow

import com.github.af2905.movieland.core.common.model.item.CreditsCastItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.CreditsCastMapper
import com.github.af2905.movieland.core.repository.TvShowsRepository
import javax.inject.Inject

class GetTvShowCredits @Inject constructor(
    private val tvShowsRepository: TvShowsRepository,
    private val mapper: CreditsCastMapper
) : CoroutineUseCase<TvShowCreditsParams, List<CreditsCastItem>?>() {

    override suspend fun execute(params: TvShowCreditsParams): List<CreditsCastItem>? {
        /*val response = tvShowsRepository.getTvShowCredits(
            tvShowId = params.tvShowId,
            language = params.language
        )
        val result = mapper.map(response)
        return result.filterNot { actorItem -> actorItem.profilePath.isNullOrEmpty() }*/
        return null
    }
}

data class TvShowCreditsParams(
    val tvShowId: Int,
    val language: String? = null
)