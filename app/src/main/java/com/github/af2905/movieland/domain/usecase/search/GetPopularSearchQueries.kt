package com.github.af2905.movieland.domain.usecase.search

import com.github.af2905.movieland.data.mapper.MovieEntityToUIListMapper
import com.github.af2905.movieland.domain.repository.MoviesRepository
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.domain.usecase.params.PopularMoviesParams
import com.github.af2905.movieland.presentation.model.item.SearchQueryItem
import javax.inject.Inject

private const val DEFAULT_SIZE = 10

class GetPopularSearchQueries @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val mapper: MovieEntityToUIListMapper
) : CoroutineUseCase<PopularMoviesParams, List<SearchQueryItem>>() {
    override suspend fun execute(params: PopularMoviesParams): List<SearchQueryItem> {
        val response = mapper.map(
            moviesRepository.getPopularMovies(
                params.language,
                params.page,
                params.region,
                params.forceUpdate
            )
        )
        return response.mapNotNull { item ->
            item.title?.let {
                SearchQueryItem(
                    id = item.id,
                    title = item.title
                )
            }
        }.take(DEFAULT_SIZE)
    }
}