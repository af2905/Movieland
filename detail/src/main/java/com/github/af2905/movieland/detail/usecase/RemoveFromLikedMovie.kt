package com.github.af2905.movieland.detail.usecase

import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.MovieDetailItemToMovieDetailMapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.detail.usecase.params.UnlikedMovieDetailParams
import javax.inject.Inject

class RemoveFromLikedMovie @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val mapper: MovieDetailItemToMovieDetailMapper
) : CoroutineUseCase<UnlikedMovieDetailParams, Boolean>() {

    override suspend fun execute(params: UnlikedMovieDetailParams): Boolean {
        return moviesRepository.removeMovieDetail(movieDetail = mapper.map(params.movieDetail))
    }
}