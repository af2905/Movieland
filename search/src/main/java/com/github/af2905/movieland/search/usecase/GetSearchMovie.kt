package com.github.af2905.movieland.search.usecase

import com.github.af2905.movieland.core.common.model.item.MoviesResponse
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.MoviesResponseDtoToUiMapper
import com.github.af2905.movieland.core.repository.SearchRepository
import com.github.af2905.movieland.search.usecase.params.SearchMovieParams
import javax.inject.Inject

class GetSearchMovie @Inject constructor(
    private val searchRepository: SearchRepository,
    private val responseDtoMapper: MoviesResponseDtoToUiMapper
) : CoroutineUseCase<SearchMovieParams, MoviesResponse>() {
    override suspend fun execute(params: SearchMovieParams): MoviesResponse {
        return responseDtoMapper.map(
            searchRepository.getSearchMovie(
                query = params.query,
                language = params.language,
                page = params.page,
                adult = params.adult,
                region = params.region,
                year = params.year
            )
        )
    }
}