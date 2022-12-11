package com.github.af2905.movieland.detail.usecase

import com.github.af2905.movieland.core.common.model.item.MovieDetailItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.MovieDetailToMovieDetailItemMapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.detail.usecase.params.GetLikedMovieDetailByIdParams
import javax.inject.Inject

class GetLikedMovieById @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val mapper: MovieDetailToMovieDetailItemMapper
) : CoroutineUseCase<GetLikedMovieDetailByIdParams, MovieDetailItem?>() {

    override suspend fun execute(params: GetLikedMovieDetailByIdParams): MovieDetailItem? {
        return moviesRepository.getMovieDetailById(params.movieId)?.let { mapper.map(it) }
    }
}