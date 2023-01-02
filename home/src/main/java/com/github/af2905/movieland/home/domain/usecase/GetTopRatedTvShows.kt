package com.github.af2905.movieland.home.domain.usecase

import com.github.af2905.movieland.core.common.model.item.TvShowItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.TvShowMapper
import com.github.af2905.movieland.core.repository.TvShowsRepository
import com.github.af2905.movieland.home.domain.params.TvShowsParams
import javax.inject.Inject

class GetTopRatedTvShows @Inject constructor(
    private val tvShowsRepository: TvShowsRepository,
    private val mapper: TvShowMapper
) : CoroutineUseCase<TvShowsParams, List<TvShowItem>>() {
    override suspend fun execute(params: TvShowsParams): List<TvShowItem> {
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