package com.github.af2905.movieland.core.shared

import com.github.af2905.movieland.core.common.model.item.MovieItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.MovieMapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.util.extension.empty
import javax.inject.Inject

class GetNowPlayingMovies @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val mapper: MovieMapper
) : CoroutineUseCase<NowPlayingMoviesParams, List<MovieItem>>() {
    override suspend fun execute(params: NowPlayingMoviesParams): List<MovieItem> {
        val response = moviesRepository.getNowPlayingMovies(
            language = params.language,
            page = params.page,
            region = params.region,
            forceUpdate = params.forceUpdate
        )
        return mapper.map(response)
            .filterNot { it.overview.isNullOrEmpty() }
            .filterNot { it.voteAverage == Double.empty }
            .sortedByDescending { it.releaseYear }
    }
}

data class NowPlayingMoviesParams(
    val language: String? = null,
    val page: Int? = null,
    val region: String? = null,
    val forceUpdate: Boolean = false
)