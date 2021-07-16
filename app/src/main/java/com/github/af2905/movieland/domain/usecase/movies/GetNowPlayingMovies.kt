package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.data.entity.MoviesResponseEntity
import com.github.af2905.movieland.data.mapper.MoviesResponseDtoToEntityMapper
import com.github.af2905.movieland.domain.repository.IMoviesRepository
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.domain.usecase.parameters.NowPlayingMoviesParams
import javax.inject.Inject

class GetNowPlayingMovies @Inject constructor(
    private val moviesRepository: IMoviesRepository,
    private val mapper: MoviesResponseDtoToEntityMapper
) : CoroutineUseCase<NowPlayingMoviesParams, MoviesResponseEntity>() {

    override suspend fun execute(params: NowPlayingMoviesParams): MoviesResponseEntity {
        return moviesRepository.getNowPlayingMovies(params.language, params.page, params.region)
            .let { mapper.map(it) }
    }
}