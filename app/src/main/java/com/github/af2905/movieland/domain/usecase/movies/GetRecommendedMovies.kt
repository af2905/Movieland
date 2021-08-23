package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.data.mapper.MoviesResponseEntityToUIMapper
import com.github.af2905.movieland.domain.repository.IMoviesRepository
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.domain.usecase.params.RecommendedMoviesParams
import com.github.af2905.movieland.presentation.model.item.MoviesResponse
import javax.inject.Inject

class GetRecommendedMovies @Inject constructor(
    private val moviesRepository: IMoviesRepository,
    private val responseEntityMapper: MoviesResponseEntityToUIMapper
) : CoroutineUseCase<RecommendedMoviesParams, MoviesResponse>() {

    override suspend fun execute(params: RecommendedMoviesParams): MoviesResponse {
        return responseEntityMapper.map(
            moviesRepository.getRecommendedMovies(params.movieId, params.language, params.page)
        )
    }
}