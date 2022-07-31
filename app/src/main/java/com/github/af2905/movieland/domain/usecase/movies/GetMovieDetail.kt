package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.core.common.model.item.MovieDetailsItem
import com.github.af2905.movieland.core.data.mapper.MovieDetailsEntityToUIMapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.domain.usecase.params.MovieDetailsParams
import javax.inject.Inject

class GetMovieDetail @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val mapper: MovieDetailsEntityToUIMapper
) : CoroutineUseCase<MovieDetailsParams, MovieDetailsItem>() {

    override suspend fun execute(params: MovieDetailsParams): MovieDetailsItem {
        return mapper.map(moviesRepository.getMovieDetails(params.movieId, params.language))
    }
}