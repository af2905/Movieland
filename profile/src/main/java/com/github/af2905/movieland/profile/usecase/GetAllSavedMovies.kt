package com.github.af2905.movieland.profile.usecase

import com.github.af2905.movieland.core.common.model.item.MovieDetailItem.Companion.mapToMovieItem
import com.github.af2905.movieland.core.common.model.item.MovieItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.MovieDetailToMovieDetailItemListMapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.profile.usecase.params.Params
import javax.inject.Inject

class GetAllSavedMovies @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val mapper: MovieDetailToMovieDetailItemListMapper
) : CoroutineUseCase<Params, List<MovieItem>>() {

    override suspend fun execute(params: Params): List<MovieItem> {
        val list = mapper.map(moviesRepository.getAllSavedMovieDetail())
        return list.map { detailItem -> detailItem.mapToMovieItem() }
    }
}