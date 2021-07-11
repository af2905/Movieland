package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.data.mapper.MoviesResponseDtoToEntityMapper
import com.github.af2905.movieland.domain.repository.IMoviesRepository
import javax.inject.Inject

class GetUpcomingMovies @Inject constructor(
    private val mapper: MoviesResponseDtoToEntityMapper,
    private val moviesRepository: IMoviesRepository
) {

    suspend operator fun invoke(
        language: String? = null, page: Int? = null, region: String? = null
    ) = moviesRepository.getUpcomingMovies(language, page, region).let { mapper.map(it) }

}