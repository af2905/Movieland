package com.github.af2905.movieland.search.domain.usecase

import com.github.af2905.movieland.core.common.model.item.SearchQueryItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.database.entity.MovieType
import com.github.af2905.movieland.core.data.mapper.MovieMapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.search.domain.params.SearchParams
import javax.inject.Inject

private const val DEFAULT_SIZE = 10

class GetPopularSearchQueries @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val mapper: MovieMapper
) : CoroutineUseCase<SearchParams, List<SearchQueryItem>>() {
    override suspend fun execute(params: SearchParams): List<SearchQueryItem> {
        val response = mapper.map(
            moviesRepository.getCachedMoviesByType(MovieType.NOW_PLAYING)
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