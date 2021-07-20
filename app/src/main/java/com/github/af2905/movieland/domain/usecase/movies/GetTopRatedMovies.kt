package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.data.entity.MoviesResponseEntity
import com.github.af2905.movieland.data.mapper.MoviesResponseDtoToEntityMapper
import com.github.af2905.movieland.domain.repository.IMoviesRepository
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.domain.usecase.parameters.TopRatedMoviesParams
import javax.inject.Inject

class GetTopRatedMovies @Inject constructor(
    private val mapper: MoviesResponseDtoToEntityMapper,
    private val moviesRepository: IMoviesRepository
) : CoroutineUseCase<TopRatedMoviesParams, MoviesResponseEntity>() {

    override suspend fun execute(params: TopRatedMoviesParams): MoviesResponseEntity {
        return moviesRepository.getTopRatedMovies(params.language, params.page, params.region)
            .let { mapper.map(it) }
    }
}