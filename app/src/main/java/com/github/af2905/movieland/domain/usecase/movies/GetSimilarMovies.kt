package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.data.mapper.MovieEntityToUIListMapper
import com.github.af2905.movieland.domain.repository.IMoviesRepository
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.domain.usecase.params.SimilarMoviesParams
import com.github.af2905.movieland.presentation.model.item.MovieItem
import javax.inject.Inject

class GetSimilarMovies @Inject constructor(
    private val moviesRepository: IMoviesRepository,
    private val mapper: MovieEntityToUIListMapper
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