package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.domain.repository.IMoviesRepository
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.presentation.feature.home.HomeRepository
import javax.inject.Inject

class ForceUpdate @Inject constructor(
    private val homeRepository: HomeRepository,
    private val movieRepository: IMoviesRepository
) : CoroutineUseCase<Unit, Unit>() {

    override suspend fun execute(params: Unit) {
        movieRepository.clearCache()
        homeRepository.forceUpdate()
    }
}