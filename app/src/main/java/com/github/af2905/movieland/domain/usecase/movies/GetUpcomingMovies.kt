package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.data.database.entity.MovieType
import com.github.af2905.movieland.data.database.entity.MoviesResponseEntity
import com.github.af2905.movieland.data.mapper.MoviesResponseDtoToEntityMapper
import com.github.af2905.movieland.data.result.Result
import com.github.af2905.movieland.domain.repository.IMoviesRepository
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.domain.usecase.params.UpcomingMoviesParams
import javax.inject.Inject

class GetUpcomingMovies @Inject constructor(
    private val mapper: MoviesResponseDtoToEntityMapper,
    private val moviesRepository: IMoviesRepository
) : CoroutineUseCase<UpcomingMoviesParams, MoviesResponseEntity>() {

    override suspend fun execute(params: UpcomingMoviesParams): Result<MoviesResponseEntity> {
        val response =
            moviesRepository.getUpcomingMovies(params.language, params.page, params.region)
        return Result.Success(mapper.map(response, MovieType.UPCOMING.name))
    }
}