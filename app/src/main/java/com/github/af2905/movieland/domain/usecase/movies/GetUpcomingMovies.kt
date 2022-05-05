package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.data.mapper.MovieEntityToUIListMapper
import com.github.af2905.movieland.domain.repository.MoviesRepository
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.domain.usecase.params.UpcomingMoviesParams
import com.github.af2905.movieland.presentation.model.item.MovieItem
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
    }
}