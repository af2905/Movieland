package com.github.af2905.movieland.detail.usecase.movie

import com.github.af2905.movieland.core.common.model.item.MovieDetailItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.MovieDetailMapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import javax.inject.Inject

class RemoveFromLikedMovie @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val mapper: MovieDetailMapper
) : CoroutineUseCase<UnlikedMovieDetailParams, Boolean>() {

    override suspend fun execute(params: UnlikedMovieDetailParams): Boolean {
        //return moviesRepository.removeMovieDetail(movieDetail = mapper.map(params.movieDetail))
        return false //TODO remove after check
    }
}

data class UnlikedMovieDetailParams(val movieDetail: MovieDetailItem)