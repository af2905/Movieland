package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.data.result.Result
import com.github.af2905.movieland.domain.repository.IMoviesRepository
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.domain.usecase.params.NowPlayingMoviesParams
import com.github.af2905.movieland.presentation.model.item.MoviesResponse
import javax.inject.Inject

class GetNowPlayingMovies @Inject constructor(
    private val moviesRepository: IMoviesRepository
) : CoroutineUseCase<NowPlayingMoviesParams, MoviesResponse>() {

    override suspend fun execute(params: NowPlayingMoviesParams): Result<MoviesResponse> {
        val response =
            moviesRepository.getNowPlayingMovies(params.language, params.page, params.region)
        return Result.Success(response)
    }
}