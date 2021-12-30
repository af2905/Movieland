package com.github.af2905.movieland.domain.usecase.adult

import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.presentation.model.item.MoviesResponse
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