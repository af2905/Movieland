package com.github.af2905.movieland.detail.usecase.movie

import com.github.af2905.movieland.core.common.model.item.MovieCreditsCastItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.MovieCreditsCastMapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.detail.usecase.params.MovieCreditsParams
import javax.inject.Inject

class GetMovieCredits @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val mapper: MovieCreditsCastMapper
) : CoroutineUseCase<MovieCreditsParams, List<MovieCreditsCastItem>>() {

    override suspend fun execute(params: MovieCreditsParams): List<MovieCreditsCastItem> {
        val response = moviesRepository.getMovieCredits(
            movieId = params.movieId,
            language = params.language
        )
        val result = mapper.map(response)
        return result.filterNot { actorItem -> actorItem.profilePath.isNullOrEmpty() }
    }
}