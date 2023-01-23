package com.github.af2905.movieland.detail.usecase.movie

import com.github.af2905.movieland.core.common.model.item.CreditsCastItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.CreditsCastMapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import javax.inject.Inject

class GetMovieCredits @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val mapper: CreditsCastMapper
) : CoroutineUseCase<MovieCreditsParams, List<CreditsCastItem>>() {

    override suspend fun execute(params: MovieCreditsParams): List<CreditsCastItem> {
        val response = moviesRepository.getMovieCredits(
            movieId = params.movieId,
            language = params.language
        )
        val result = mapper.map(response)
        return result.filterNot { actorItem -> actorItem.profilePath.isNullOrEmpty() }
    }
}

data class MovieCreditsParams(val movieId: Int, val language: String? = null)