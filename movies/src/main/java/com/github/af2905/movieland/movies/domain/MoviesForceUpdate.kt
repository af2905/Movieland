package com.github.af2905.movieland.movies.domain

import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.movies.repository.MoviesForceUpdateRepository
import javax.inject.Inject

class MoviesForceUpdate @Inject constructor(
    private val moviesForceUpdateRepository: MoviesForceUpdateRepository
) : CoroutineUseCase<Unit, Unit>() {
    override suspend fun execute(params: Unit) = moviesForceUpdateRepository.forceUpdate()
}