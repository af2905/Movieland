package com.github.af2905.movieland.detail.usecase.tvshow

import com.github.af2905.movieland.core.common.model.item.TvShowItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.TvShowMapper
import com.github.af2905.movieland.core.repository.TvShowsRepository
import javax.inject.Inject

class GetSimilarTvShows @Inject constructor(
    private val tvShowsRepository: TvShowsRepository,
    private val mapper: TvShowMapper
) : CoroutineUseCase<SimilarTvShowsParams, List<TvShowItem>?>() {

    override suspend fun execute(params: SimilarTvShowsParams): List<TvShowItem>? {
/*        val response = tvShowsRepository.getSimilarTvShows(
            tvShowId = params.tvShowId,
            language = params.language
        )
        val result = mapper.map(response)
        return mapper.map(result).filterNot { tvShowItem -> tvShowItem.posterPath.isNullOrEmpty() }*/
        return null
    }
}

data class SimilarTvShowsParams(
    val tvShowId: Int,
    val language: String? = null
)
