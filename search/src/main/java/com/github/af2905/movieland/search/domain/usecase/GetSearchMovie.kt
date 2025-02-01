package com.github.af2905.movieland.search.domain.usecase

import com.github.af2905.movieland.core.common.model.item.MovieItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.MovieMapper
import com.github.af2905.movieland.core.repository.SearchRepository
import com.github.af2905.movieland.search.domain.params.SearchMovieParams
import javax.inject.Inject

class GetSearchMovie @Inject constructor(
    private val searchRepository: SearchRepository,
    private val mapper: MovieMapper
) : CoroutineUseCase<SearchMovieParams, List<MovieItem>>() {
    override suspend fun execute(params: SearchMovieParams): List<MovieItem> {
        /*val response = searchRepository.getSearchMovie(
            query = params.query,
            language = params.language,
            page = params.page,
            adult = params.adult,
            region = params.region,
            year = params.year
        )
        val list = mapper.map(response)
        return mapper.map(list)*/
        return emptyList()
    }
}