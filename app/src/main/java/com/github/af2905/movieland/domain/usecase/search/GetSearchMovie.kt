package com.github.af2905.movieland.domain.usecase.search

import com.github.af2905.movieland.data.mapper.MoviesResponseDtoToUiMapper
import com.github.af2905.movieland.data.repository.SearchRepositoryImpl
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.domain.usecase.params.SearchMovieParams
import com.github.af2905.movieland.presentation.model.item.MoviesResponse
import javax.inject.Inject

class GetSearchMovie @Inject constructor(
    private val searchRepository: SearchRepositoryImpl,
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