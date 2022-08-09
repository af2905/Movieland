package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.core.common.model.item.MovieItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.MovieEntityToUIListMapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.domain.usecase.params.PopularMoviesParams
import com.github.af2905.movieland.util.extension.empty
import javax.inject.Inject

class GetPopularMovies @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val mapper: MovieEntityToUIListMapper
) : CoroutineUseCase<PopularMoviesParams, List<MovieItem>>() {
    override suspend fun execute(params: PopularMoviesParams): List<MovieItem> {
        val response = moviesRepository.getPopularMovies(
            params.language,
            params.page,
            params.region,
            params.forceUpdate
        )
        return mapper.map(response)
            .filterNot { it.overview.isNullOrEmpty() }
            .filterNot { it.voteAverage == Double.empty }
            .sortedByDescending { it.releaseYear }
    }
}