package com.github.af2905.movieland.home.domain.usecase

import com.github.af2905.movieland.core.common.model.item.MovieItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.MovieMapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.home.domain.params.CachedMoviesParams
import javax.inject.Inject

class GetCachedMoviesByType @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val mapper: MovieMapper
) : CoroutineUseCase<CachedMoviesParams, List<MovieItem>>() {
    override suspend fun execute(params: CachedMoviesParams): List<MovieItem> {
        val movies = moviesRepository.getCachedMoviesByType(params.type)
        return mapper.map(movies)
    }
}