package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.domain.repository.IMoviesRepository
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.domain.usecase.params.PopularMoviesParams
import com.github.af2905.movieland.presentation.model.item.MoviesResponse
import javax.inject.Inject

class GetPopularMovies @Inject constructor(private val moviesRepository: IMoviesRepository) :
    CoroutineUseCase<PopularMoviesParams, MoviesResponse>() {

    override suspend fun execute(params: PopularMoviesParams): MoviesResponse {
        return moviesRepository.getPopularMovies(params.language, params.page, params.region)
    }
}