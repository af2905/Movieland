package com.github.af2905.movieland.detail.usecase

import com.github.af2905.movieland.core.common.model.item.MovieDetailItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.MovieDetailToMovieDetailItemMapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.detail.usecase.params.MovieDetailsParams

import javax.inject.Inject

class GetMovieDetail @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val mapper: MovieDetailToMovieDetailItemMapper
) : CoroutineUseCase<MovieDetailsParams, MovieDetailItem>() {

    override suspend fun execute(params: MovieDetailsParams): MovieDetailItem {
        return mapper.map(
            moviesRepository.getMovieDetail(
                movieId = params.movieId,
                language = params.language
            )
        )
    }
}