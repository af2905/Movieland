package com.github.af2905.movieland.home.domain.usecase

import com.github.af2905.movieland.core.common.model.item.MoviesResponse
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import javax.inject.Inject

class GetAdultMovies @Inject constructor() :
    CoroutineUseCase<suspend () -> Result<MoviesResponse>, MoviesResponse>() {
    override suspend fun execute(params: suspend () -> Result<MoviesResponse>): MoviesResponse {
        val result = params.invoke()
        val response = result.getOrThrow()
        val adultMovies = response.movies?.filter { it.adult == true }
        return response.copy(movies = adultMovies)
    }
}