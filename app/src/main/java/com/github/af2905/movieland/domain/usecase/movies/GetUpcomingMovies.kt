package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.data.result.Result
import com.github.af2905.movieland.domain.repository.IMoviesRepository
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.domain.usecase.params.UpcomingMoviesParams
import com.github.af2905.movieland.presentation.model.item.MoviesResponse
import javax.inject.Inject

class GetUpcomingMovies @Inject constructor(
    private val moviesRepository: IMoviesRepository
) : CoroutineUseCase<UpcomingMoviesParams, MoviesResponse>() {

    override suspend fun execute(params: UpcomingMoviesParams): Result<MoviesResponse> {
        val response =
            moviesRepository.getUpcomingMovies(params.language, params.page, params.region)
        return Result.Success(response)
    }
}