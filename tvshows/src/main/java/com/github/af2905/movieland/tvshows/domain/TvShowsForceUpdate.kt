package com.github.af2905.movieland.tvshows.domain

import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.tvshows.repository.TvShowsForceUpdateRepository
import javax.inject.Inject

class TvShowsForceUpdate @Inject constructor(
    private val forceUpdateRepository: TvShowsForceUpdateRepository
) : CoroutineUseCase<Unit, Unit>() {
    override suspend fun execute(params: Unit) = forceUpdateRepository.forceUpdate()
}