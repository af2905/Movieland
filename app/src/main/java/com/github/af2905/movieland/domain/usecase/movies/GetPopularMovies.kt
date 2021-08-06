package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.data.entity.MoviesResponseEntity
import com.github.af2905.movieland.data.error.Result
import com.github.af2905.movieland.data.mapper.MoviesResponseDtoToEntityMapper
import com.github.af2905.movieland.domain.repository.IMoviesRepository
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.domain.usecase.params.PopularMoviesParams
import javax.inject.Inject

class GetPopularMovies @Inject constructor(
    private val mapper: MoviesResponseDtoToEntityMapper,
    private val moviesRepository: IMoviesRepository
) : CoroutineUseCase<PopularMoviesParams, MoviesResponseEntity>() {

    override suspend fun execute(params: PopularMoviesParams): Result<MoviesResponseEntity> {
        val response =
            moviesRepository.getPopularMovies(params.language, params.page, params.region)
        return Result.Success(mapper.map(response))
    }
}