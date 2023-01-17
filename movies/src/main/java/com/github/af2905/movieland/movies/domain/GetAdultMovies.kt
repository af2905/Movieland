package com.github.af2905.movieland.movies.domain

import com.github.af2905.movieland.core.common.model.item.MovieItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import javax.inject.Inject

class GetAdultMovies @Inject constructor() :
    CoroutineUseCase<suspend () -> Result<List<MovieItem>>, List<MovieItem>>() {
    override suspend fun execute(params: suspend () -> Result<List<MovieItem>>): List<MovieItem> {
        val result = params.invoke()
        val response = result.getOrThrow()
        return response.filter { it.adult == true }
    }
}