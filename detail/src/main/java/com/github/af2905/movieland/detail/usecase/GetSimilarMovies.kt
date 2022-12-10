package com.github.af2905.movieland.detail.usecase

import com.github.af2905.movieland.core.common.model.item.MovieItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.MovieToMovieItemListMapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.detail.usecase.params.SimilarMoviesParams

import javax.inject.Inject

class GetSimilarMovies @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val mapper: MovieToMovieItemListMapper
) : CoroutineUseCase<SimilarMoviesParams, List<MovieItem>>() {

    override suspend fun execute(params: SimilarMoviesParams): List<MovieItem> {
        val response = moviesRepository.getSimilarMovies(
            params.movieId,
            params.language,
            params.page
        )
        return mapper.map(response)
    }
}