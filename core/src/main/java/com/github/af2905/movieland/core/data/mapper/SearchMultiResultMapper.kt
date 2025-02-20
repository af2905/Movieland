package com.github.af2905.movieland.core.data.mapper

import com.github.af2905.movieland.core.data.database.entity.KnownFor
import com.github.af2905.movieland.core.data.database.entity.MediaType
import com.github.af2905.movieland.core.data.database.entity.SearchMultiResult
import com.github.af2905.movieland.core.data.dto.people.KnownForDto
import com.github.af2905.movieland.core.data.dto.search.SearchMultiResultDto
import javax.inject.Inject

class SearchMultiResultMapper @Inject constructor() {

    fun map(dto: SearchMultiResultDto): SearchMultiResult {
        return SearchMultiResult(
            id = dto.id,
            mediaType = MediaType.fromString(dto.mediaType) ?: MediaType.MOVIE,
            name = dto.name,
            title = dto.title,
            overview = dto.overview,
            backdropPath = dto.backdropPath,
            firstAirDate = dto.firstAirDate,
            releaseDate = dto.releaseDate,
            genreIds = dto.genreIds,
            originCountry = dto.originCountry,
            originalLanguage = dto.originalLanguage,
            originalName = dto.originalName,
            popularity = dto.popularity,
            posterPath = dto.posterPath,
            voteAverage = dto.voteAverage,
            voteCount = dto.voteCount,
            profilePath = dto.profilePath,
            knownFor = dto.knownFor?.map { mapKnownFor(it) } ?: emptyList()
        )
    }

    private fun mapKnownFor(dto: KnownForDto): KnownFor {
        return KnownFor(
            id = dto.id,
            mediaType = MediaType.fromString(dto.mediaType) ?: MediaType.MOVIE,
            title = dto.title,
            backdropPath = dto.backdropPath,
            overview = dto.overview,
            voteAverage = dto.voteAverage,
            voteCount = dto.voteCount,
            genreIds = dto.genreIds,
            firstAirDate = dto.firstAirDate,
            name = dto.name,
            originalName = dto.originalName,
            originCountry = dto.originCountry,
            originalLanguage = dto.originalLanguage,
            releaseDate = dto.releaseDate,
            posterPath = dto.posterPath
        )
    }

    fun mapList(dtoList: List<SearchMultiResultDto>): List<SearchMultiResult> {
        return dtoList.map { map(it) }
    }
}