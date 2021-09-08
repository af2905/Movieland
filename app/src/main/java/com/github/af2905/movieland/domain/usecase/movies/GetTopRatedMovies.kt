package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.data.mapper.MoviesResponseEntityToUIMapper
import com.github.af2905.movieland.domain.repository.IMoviesRepository
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.domain.usecase.params.TopRatedMoviesParams
import com.github.af2905.movieland.presentation.model.item.MoviesResponse
import javax.inject.Inject

class GetTopRatedMovies @Inject constructor(
    private val moviesRepository: IMoviesRepository,
    private val responseEntityMapper: MoviesResponseEntityToUIMapper
) : CoroutineUseCase<TopRatedMoviesParams, MoviesResponse>() {

    override suspend fun execute(params: TopRatedMoviesParams): MoviesResponse {
        return responseEntityMapper.map(
            moviesRepository.getTopRatedMovies(params.language, params.page, params.region)
        )
    }
}