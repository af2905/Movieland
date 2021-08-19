package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.domain.repository.IMoviesRepository
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.domain.usecase.params.SimilarMoviesParams
import com.github.af2905.movieland.presentation.model.item.MoviesResponse
import javax.inject.Inject

class GetSimilarMovies @Inject constructor(
    private val moviesRepository: IMoviesRepository
) : CoroutineUseCase<SimilarMoviesParams, MoviesResponse>() {

    override suspend fun execute(params: SimilarMoviesParams): MoviesResponse {
        return moviesRepository.getRecommendedMovies(params.movieId, params.language, params.page)

    }
}