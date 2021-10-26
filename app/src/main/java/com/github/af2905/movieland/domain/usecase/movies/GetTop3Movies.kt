package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.data.result.Result
import com.github.af2905.movieland.data.result.getOrThrow
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.presentation.model.item.MoviesResponse
import javax.inject.Inject

private const val DEFAULT_TAKE = 3
private const val DEFAULT_MIN_POPULARITY = 7.0

class GetTop3Movies @Inject constructor() :
    CoroutineUseCase<suspend () -> Result<MoviesResponse>, MoviesResponse>() {
    override suspend fun execute(params: suspend () -> Result<MoviesResponse>): MoviesResponse {
        val result = params.invoke()
        val response = result.getOrThrow()
        val top3 = response.movies
            ?.filterNot { it.voteAverage == null }
            ?.filter { it.voteAverage!! >= DEFAULT_MIN_POPULARITY }
            ?.take(DEFAULT_TAKE)
            .orEmpty()
        return response.copy(movies = top3)
    }
}