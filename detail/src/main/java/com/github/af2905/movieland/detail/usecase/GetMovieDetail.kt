package com.github.af2905.movieland.detail.usecase

import com.github.af2905.movieland.core.common.model.item.MovieDetailItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.MovieDetailToMovieDetailItemMapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.detail.usecase.params.MovieDetailParams

import javax.inject.Inject

class GetMovieDetail @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val mapper: MovieDetailToMovieDetailItemMapper
) : CoroutineUseCase<MovieDetailParams, MovieDetailItem>() {

    override suspend fun execute(params: MovieDetailParams): MovieDetailItem {
        return mapper.map(
            moviesRepository.getMovieDetail(
                movieId = params.movieId,
                language = params.language
            )
        )
    }
}