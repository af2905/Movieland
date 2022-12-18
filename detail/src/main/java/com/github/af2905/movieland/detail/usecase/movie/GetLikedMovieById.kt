package com.github.af2905.movieland.detail.usecase.movie

import com.github.af2905.movieland.core.common.model.item.MovieDetailItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.MovieDetailMapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.detail.usecase.params.GetLikedMovieDetailByIdParams
import javax.inject.Inject

class GetLikedMovieById @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val mapper: MovieDetailMapper
) : CoroutineUseCase<GetLikedMovieDetailByIdParams, MovieDetailItem?>() {

    override suspend fun execute(params: GetLikedMovieDetailByIdParams): MovieDetailItem? {
        return moviesRepository.getMovieDetailById(params.movieId)?.let { mapper.map(it) }
    }
}