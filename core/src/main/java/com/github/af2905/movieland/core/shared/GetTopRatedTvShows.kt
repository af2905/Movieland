package com.github.af2905.movieland.core.shared

import com.github.af2905.movieland.core.common.model.item.TvShowItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.TvShowMapper
import com.github.af2905.movieland.core.repository.TvShowsRepository
import javax.inject.Inject

class GetTopRatedTvShows @Inject constructor(
    private val tvShowsRepository: TvShowsRepository,
    private val mapper: TvShowMapper
) : CoroutineUseCase<TopRatedTvShowsParams, List<TvShowItem>>() {
    override suspend fun execute(params: TopRatedTvShowsParams): List<TvShowItem> {
        val response = tvShowsRepository.getTopRatedTvShows(
            language = params.language,
            page = params.page,
            forceUpdate = params.forceUpdate
        )
        return mapper.map(response)
            .filterNot { it.overview.isNullOrEmpty() }
            .sortedByDescending { it.releaseYear }
    }
}

data class TopRatedTvShowsParams(
    val language: String? = null,
    val page: Int? = null,
    val forceUpdate: Boolean = false
)