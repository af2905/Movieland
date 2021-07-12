package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.data.mapper.MoviesResponseDtoToEntityMapper
import com.github.af2905.movieland.domain.repository.IMoviesRepository
import javax.inject.Inject

class GetSimilarMovies @Inject constructor(
    private val moviesRepository: IMoviesRepository,
    private val mapper: MoviesResponseDtoToEntityMapper
) {

    suspend operator fun invoke(
        movieId: Int, language: String? = null, page: Int? = null
    ) = moviesRepository.getSimilarMovies(movieId, language, page).let { mapper.map(it) }

}