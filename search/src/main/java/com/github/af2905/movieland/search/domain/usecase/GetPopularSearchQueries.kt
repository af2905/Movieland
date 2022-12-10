package com.github.af2905.movieland.search.domain.usecase

import com.github.af2905.movieland.core.common.model.item.SearchQueryItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.MovieToMovieItemListMapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.search.domain.params.PopularMoviesParams
import javax.inject.Inject

private const val DEFAULT_SIZE = 10

class GetPopularSearchQueries @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val mapper: MovieToMovieItemListMapper
) : CoroutineUseCase<PopularMoviesParams, List<SearchQueryItem>>() {
    override suspend fun execute(params: PopularMoviesParams): List<SearchQueryItem> {
        val response = mapper.map(
            moviesRepository.getPopularMovies(
                language = params.language,
                page = params.page,
                region = params.region,
                forceUpdate = params.forceUpdate
            )
        )
        return response.mapNotNull { item ->
            item.title?.let {
                SearchQueryItem(
                    id = item.id,
                    title = it
                )
            }
        }.take(DEFAULT_SIZE)
    }
}