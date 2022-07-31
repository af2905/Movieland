package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.core.common.model.item.MovieItem
import com.github.af2905.movieland.core.data.mapper.MovieEntityToUIListMapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.domain.usecase.params.RecommendedMoviesParams
import javax.inject.Inject

class GetRecommendedMovies @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val mapper: MovieEntityToUIListMapper
) : CoroutineUseCase<RecommendedMoviesParams, List<MovieItem>>() {

    override suspend fun execute(params: RecommendedMoviesParams): List<MovieItem> {
        val response = moviesRepository.getRecommendedMovies(
            params.movieId,
            params.language,
            params.page
        )
        return mapper.map(response)
    }
}