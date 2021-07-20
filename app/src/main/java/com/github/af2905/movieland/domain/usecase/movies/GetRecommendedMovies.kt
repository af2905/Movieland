package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.data.entity.MoviesResponseEntity
import com.github.af2905.movieland.data.mapper.MoviesResponseDtoToEntityMapper
import com.github.af2905.movieland.domain.repository.IMoviesRepository
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.domain.usecase.parameters.RecommendedMoviesParams
import javax.inject.Inject

class GetRecommendedMovies @Inject constructor(
    private val moviesRepository: IMoviesRepository,
    private val mapper: MoviesResponseDtoToEntityMapper
) : CoroutineUseCase<RecommendedMoviesParams, MoviesResponseEntity>() {

    override suspend fun execute(params: RecommendedMoviesParams): MoviesResponseEntity {
        return moviesRepository.getRecommendedMovies(params.movieId, params.language, params.page)
            .let { mapper.map(it) }
    }
}