package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.data.mapper.MovieActorDtoMapper
import com.github.af2905.movieland.domain.repository.MoviesRepository
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.domain.usecase.params.MovieActorsParams
import com.github.af2905.movieland.presentation.model.item.MovieActorItem
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