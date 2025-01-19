package com.github.af2905.movieland.core.shared

import com.github.af2905.movieland.core.common.model.item.TvShowItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.TvShowMapper
import com.github.af2905.movieland.core.repository.TvShowsRepository
import com.github.af2905.movieland.util.extension.empty
import javax.inject.Inject

class GetPopularTvShows @Inject constructor(
    private val tvShowsRepository: TvShowsRepository,
    private val mapper: TvShowMapper
) : CoroutineUseCase<PopularTvShowsParams, List<TvShowItem>>() {
    override suspend fun execute(params: PopularTvShowsParams): List<TvShowItem> {
        /*val response = tvShowsRepository.getPopularTvShows(
            language = params.language,
            page = params.page,
            forceUpdate = params.forceUpdate
        )
        return mapper.map(response)
            .filter { !it.overview.isNullOrEmpty() && it.voteAverage != Double.empty && it.posterFullPathToImage != null }
            .sortedByDescending { it.releaseYear }*/
        return emptyList()
    }
}

data class PopularTvShowsParams(
    val language: String? = null,
    val page: Int? = null,
    val forceUpdate: Boolean = false
)