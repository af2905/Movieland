package com.github.af2905.movieland.detail.usecase

import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.MovieDetailItemToMovieDetailMapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.detail.usecase.params.LikedMovieDetailParams
import javax.inject.Inject

class SaveToLikedMovie @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val mapper: MovieDetailItemToMovieDetailMapper
) : CoroutineUseCase<LikedMovieDetailParams, Boolean>() {

    override suspend fun execute(params: LikedMovieDetailParams): Boolean {
        return moviesRepository.saveMovieDetail(movieDetail = mapper.map(params.movieDetail))
    }
}