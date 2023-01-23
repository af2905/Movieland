package com.github.af2905.movieland.detail.usecase.movie

import com.github.af2905.movieland.core.common.model.item.MovieItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.MovieMapper
import com.github.af2905.movieland.core.repository.MoviesRepository

import javax.inject.Inject

class GetSimilarMovies @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val mapper: MovieMapper
) : CoroutineUseCase<SimilarMoviesParams, List<MovieItem>>() {

    override suspend fun execute(params: SimilarMoviesParams): List<MovieItem> {
        val response = moviesRepository.getSimilarMovies(
            params.movieId,
            params.language,
            params.page
        )
        val result = mapper.map(response)
        return mapper.map(result).filterNot { movieItem -> movieItem.posterPath.isNullOrEmpty() }
    }
}

data class SimilarMoviesParams(
    val movieId: Int,
    val language: String? = null,
    val page: Int? = null
)