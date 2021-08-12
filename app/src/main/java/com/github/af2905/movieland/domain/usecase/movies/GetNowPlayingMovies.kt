package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.data.database.entity.MovieType
import com.github.af2905.movieland.data.database.entity.MoviesResponseEntity
import com.github.af2905.movieland.data.mapper.MoviesResponseDtoToEntityMapper
import com.github.af2905.movieland.data.result.Result
import com.github.af2905.movieland.domain.repository.IMoviesRepository
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.domain.usecase.params.NowPlayingMoviesParams
import javax.inject.Inject

class GetNowPlayingMovies @Inject constructor(
    private val moviesRepository: IMoviesRepository,
    private val mapper: MoviesResponseDtoToEntityMapper
) : CoroutineUseCase<NowPlayingMoviesParams, MoviesResponseEntity>() {

    override suspend fun execute(params: NowPlayingMoviesParams): Result<MoviesResponseEntity> {
        val response =
            moviesRepository.getNowPlayingMovies(params.language, params.page, params.region)
        return Result.Success(mapper.map(response, MovieType.NOW_PLAYING.name))
    }
}