package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.data.mapper.MoviesResponseDtoToEntityMapper
import com.github.af2905.movieland.domain.repository.IMoviesRepository
import javax.inject.Inject

class GetNowPlayingMovies @Inject constructor(
    private val moviesRepository: IMoviesRepository,
    private val mapper: MoviesResponseDtoToEntityMapper
) {

    suspend operator fun invoke(
        language: String? = null, page: Int? = null, region: String? = null
    ) = moviesRepository.getNowPlayingMovies(language, page, region).let { mapper.map(it) }

}