package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.data.mapper.MovieEntityToUIListMapper
import com.github.af2905.movieland.domain.repository.IMoviesRepository
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.domain.usecase.params.TopRatedMoviesParams
import com.github.af2905.movieland.presentation.model.item.MovieItem
import javax.inject.Inject

class GetTopRatedMovies @Inject constructor(
    private val moviesRepository: IMoviesRepository,
    private val mapper: MovieEntityToUIListMapper
) : CoroutineUseCase<TopRatedMoviesParams, List<MovieItem>>() {
    override suspend fun execute(params: TopRatedMoviesParams): List<MovieItem> {
        val response = moviesRepository.getTopRatedMovies(
            params.language,
            params.page,
            params.region,
            params.forceUpdate
        )
        return mapper.map(response)
    }
}