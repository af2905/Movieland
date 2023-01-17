package com.github.af2905.movieland.liked.domain

import com.github.af2905.movieland.core.common.model.item.MovieDetailItem
import com.github.af2905.movieland.core.common.model.item.MovieDetailItem.Companion.mapToMovieItem
import com.github.af2905.movieland.core.common.model.item.MovieItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.MovieDetailMapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import javax.inject.Inject

class GetAllSavedMovies @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val mapper: MovieDetailMapper
) : CoroutineUseCase<Unit, List<MovieItem>>() {

    override suspend fun execute(params: Unit): List<MovieItem> {
        val response = moviesRepository.getAllSavedMovieDetail()
        val list: List<MovieDetailItem> = response.map { movieDetail -> mapper.map(movieDetail) }
        return list.map { detailItem -> detailItem.mapToMovieItem() }
    }
}