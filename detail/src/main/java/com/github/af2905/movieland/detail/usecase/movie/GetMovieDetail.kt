package com.github.af2905.movieland.detail.usecase.movie

import com.github.af2905.movieland.core.common.model.item.MovieDetailItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.MovieDetailMapper
import com.github.af2905.movieland.core.repository.MoviesRepository

import javax.inject.Inject

class GetMovieDetail @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val mapper: MovieDetailMapper
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

data class MovieDetailParams(val movieId: Int, val language: String? = null)