package com.github.af2905.movieland.liked.domain

import com.github.af2905.movieland.core.common.model.item.TvShowDetailItem
import com.github.af2905.movieland.core.common.model.item.TvShowDetailItem.Companion.mapToTvShowItem
import com.github.af2905.movieland.core.common.model.item.TvShowItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.TvShowDetailMapper
import com.github.af2905.movieland.core.repository.TvShowsRepository
import javax.inject.Inject

class GetAllSavedTvShows @Inject constructor(
    private val tvShowsRepository: TvShowsRepository,
    private val mapper: TvShowDetailMapper
) : CoroutineUseCase<Unit, List<TvShowItem>>() {
    override suspend fun execute(params: Unit): List<TvShowItem> {
        val response = tvShowsRepository.getAllSavedTvShowDetail()
        val list: List<TvShowDetailItem> = response.map { tvShowDetail -> mapper.map(tvShowDetail) }
        return list.map { detailItem -> detailItem.mapToTvShowItem() }
    }
}