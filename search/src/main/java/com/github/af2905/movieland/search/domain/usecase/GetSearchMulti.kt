package com.github.af2905.movieland.search.domain.usecase

import com.github.af2905.movieland.core.common.model.item.SearchMultiItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.MediaType
import com.github.af2905.movieland.core.data.mapper.SearchMultiToSearchMultiItemListMapper
import com.github.af2905.movieland.core.repository.SearchRepository
import com.github.af2905.movieland.search.domain.params.SearchMultiParams
import javax.inject.Inject

class GetSearchMulti @Inject constructor(
    private val searchRepository: SearchRepository,
    private val mapper: SearchMultiToSearchMultiItemListMapper
) : CoroutineUseCase<SearchMultiParams, List<SearchMultiItem>>() {
    override suspend fun execute(params: SearchMultiParams): List<SearchMultiItem> {
        return mapper.map(
            searchRepository.getSearchMulti(
                query = params.query,
                language = params.language,
                page = params.page,
                adult = params.adult,
                region = params.region
            )
        ).filter {
            (it.mediaType == MediaType.PERSON.type && it.profilePath != null) ||
                    ((it.mediaType == MediaType.MOVIE.type || it.mediaType == MediaType.TV.type)
                            && it.posterPath != null
                            && it.voteAverage != null
                            && !it.overview.isNullOrEmpty())
        }
    }
}