package com.github.af2905.movieland.domain.usecase.movies

import com.github.af2905.movieland.data.mapper.MovieDetailsDtoToEntityMapper
import com.github.af2905.movieland.domain.repository.IMoviesRepository
import javax.inject.Inject

class GetMovieDetails @Inject constructor(
    private val moviesRepository: IMoviesRepository,
    private val mapper: MovieDetailsDtoToEntityMapper
) {
    suspend operator fun invoke(movieId: Int, language: String? = null) =
        moviesRepository.getMovieDetails(movieId, language).let { mapper.map(it) }
}