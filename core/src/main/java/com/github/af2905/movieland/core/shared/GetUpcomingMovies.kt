package com.github.af2905.movieland.core.shared

import com.github.af2905.movieland.core.common.model.item.MovieItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.MovieMapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import javax.inject.Inject

class GetUpcomingMovies @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val mapper: MovieMapper
) : CoroutineUseCase<UpcomingMoviesParams, List<MovieItem>>() {
    override suspend fun execute(params: UpcomingMoviesParams): List<MovieItem> {
        val response = moviesRepository.getUpcomingMovies(
            language = params.language,
            page = params.page,
            region = params.region,
            forceUpdate = params.forceUpdate
        )
        return mapper.map(response).filterNot { it.overview.isNullOrEmpty() }
            .sortedByDescending { it.releaseYear }
    }
}

data class UpcomingMoviesParams(
    val language: String? = null,
    val page: Int? = null,
    val region: String? = null,
    val forceUpdate: Boolean = false
)