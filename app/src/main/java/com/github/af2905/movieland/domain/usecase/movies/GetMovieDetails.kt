package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.data.entity.MovieDetailsEntity
import com.github.af2905.movieland.data.error.Result
import com.github.af2905.movieland.data.mapper.MovieDetailsDtoToEntityMapper
import com.github.af2905.movieland.domain.repository.IMoviesRepository
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.domain.usecase.params.MovieDetailsParams
import javax.inject.Inject

class GetMovieDetails @Inject constructor(
    private val moviesRepository: IMoviesRepository,
    private val mapper: MovieDetailsDtoToEntityMapper
) : CoroutineUseCase<MovieDetailsParams, MovieDetailsEntity>() {

    override suspend fun execute(params: MovieDetailsParams): Result<MovieDetailsEntity> {
        val response = moviesRepository.getMovieDetails(params.movieId, params.language)
        return Result.Success(mapper.map(response))
    }
}