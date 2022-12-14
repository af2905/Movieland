package com.github.af2905.movieland.detail.usecase

import com.github.af2905.movieland.core.common.model.item.MovieActorItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.MovieActorToMovieActorItemMapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.detail.usecase.params.MovieCreditsParams
import javax.inject.Inject

class GetMovieCredits @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val mapper: MovieActorToMovieActorItemMapper
) : CoroutineUseCase<MovieCreditsParams, List<MovieActorItem>>() {

    override suspend fun execute(params: MovieCreditsParams): List<MovieActorItem> {
        return moviesRepository.getMovieCredits(
            movieId = params.movieId,
            language = params.language
        ).map { mapper.map(it) }
    }
}