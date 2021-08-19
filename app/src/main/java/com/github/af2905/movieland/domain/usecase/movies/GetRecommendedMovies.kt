package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.domain.repository.IMoviesRepository
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.domain.usecase.params.RecommendedMoviesParams
import com.github.af2905.movieland.presentation.model.item.MoviesResponse
import javax.inject.Inject

class GetRecommendedMovies @Inject constructor(
    private val moviesRepository: IMoviesRepository
) : CoroutineUseCase<RecommendedMoviesParams, MoviesResponse>() {

    override suspend fun execute(params: RecommendedMoviesParams): MoviesResponse {
        return moviesRepository.getRecommendedMovies(params.movieId, params.language, params.page)
    }
}