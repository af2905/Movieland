package com.github.af2905.movieland.detail.usecase

import com.github.af2905.movieland.core.common.model.item.MovieActorItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.MovieActorDtoMapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import javax.inject.Inject

class GetMovieActors @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val movieActorMapper: MovieActorDtoMapper
) : CoroutineUseCase<MovieActorsParams, List<MovieActorItem>>() {

    override suspend fun execute(params: MovieActorsParams): List<MovieActorItem> {
        return moviesRepository.getMovieActors(params.movieId, params.language).actors
            .map { movieActorMapper.map(it) }
    }
}