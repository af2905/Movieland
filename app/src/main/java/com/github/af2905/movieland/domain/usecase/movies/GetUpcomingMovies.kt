package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.domain.repository.IMoviesRepository
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.domain.usecase.params.UpcomingMoviesParams
import com.github.af2905.movieland.presentation.model.item.MoviesResponse
import javax.inject.Inject

class GetUpcomingMovies @Inject constructor(
    private val moviesRepository: IMoviesRepository
) : CoroutineUseCase<UpcomingMoviesParams, MoviesResponse>() {

    override suspend fun execute(params: UpcomingMoviesParams): MoviesResponse {

        return moviesRepository.getUpcomingMovies(params.language, params.page, params.region)

    }
}