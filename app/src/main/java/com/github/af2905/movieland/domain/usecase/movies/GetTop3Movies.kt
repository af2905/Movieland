package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.presentation.model.item.MoviesResponse
import javax.inject.Inject

class GetTop3Movies @Inject constructor() :
    CoroutineUseCase<suspend () -> Result<MoviesResponse>, MoviesResponse>() {
    override suspend fun execute(params: suspend () -> Result<MoviesResponse>): MoviesResponse {
        val result = params.invoke()
        val response = result.getOrThrow()
        val top3 = response.movies
            ?.filterNot { it.popularity == null }
            ?.filter { it.popularity!! > 7.0 }
            ?.take(3)
            .orEmpty()
        return response.copy(movies = top3)
    }
}