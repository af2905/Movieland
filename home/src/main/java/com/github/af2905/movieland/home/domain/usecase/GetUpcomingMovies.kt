package com.github.af2905.movieland.home.domain.usecase

import com.github.af2905.movieland.core.common.model.item.MovieItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.MovieEntityToUIListMapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.home.domain.params.UpcomingMoviesParams
import javax.inject.Inject

class GetUpcomingMovies @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val mapper: MovieEntityToUIListMapper
) : CoroutineUseCase<UpcomingMoviesParams, List<MovieItem>>() {
    override suspend fun execute(params: UpcomingMoviesParams): List<MovieItem> {
        val response = moviesRepository.getUpcomingMovies(
            params.language,
            params.page,
            params.region,
            params.forceUpdate
        )
        return mapper.map(response)
            .filterNot { it.overview.isNullOrEmpty() }
            .sortedByDescending { it.releaseYear }
    }
}