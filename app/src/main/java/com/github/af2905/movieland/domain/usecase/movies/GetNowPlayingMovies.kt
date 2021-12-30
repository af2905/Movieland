package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.data.mapper.MoviesResponseEntityToUIMapper
import com.github.af2905.movieland.domain.repository.IMoviesRepository
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.domain.usecase.params.NowPlayingMoviesParams
import com.github.af2905.movieland.presentation.model.item.MoviesResponse
import javax.inject.Inject

class GetNowPlayingMovies @Inject constructor(
    private val moviesRepository: IMoviesRepository,
    private val responseEntityMapper: MoviesResponseEntityToUIMapper
) : CoroutineUseCase<NowPlayingMoviesParams, MoviesResponse>() {

    override suspend fun execute(params: NowPlayingMoviesParams): MoviesResponse {
        return responseEntityMapper.map(
            moviesRepository.getNowPlayingMovies(
                params.language,
                params.page,
                params.region
            )
        )
    }
}