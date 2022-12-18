package com.github.af2905.movieland.core.data.mapper

import com.github.af2905.movieland.core.common.model.item.SearchMultiItem
import com.github.af2905.movieland.core.data.database.entity.plain.KnownFor
import com.github.af2905.movieland.core.data.dto.people.KnownForDto
import com.github.af2905.movieland.core.data.dto.search.SearchMultiResultDto
import javax.inject.Inject

class SearchMultiMapper @Inject constructor(
    private val knownForMapper: KnownForMapper
) {
    fun map(input: List<SearchMultiResultDto>): List<SearchMultiItem> =
        input.map { dto -> map(dto) }

    private fun map(input: SearchMultiResultDto): SearchMultiItem = with(input) {
        SearchMultiItem(
            id = id,
            name = name,
            title = title,
            mediaType = mediaType,
            overview = overview,
            backdropPath = backdropPath,
            firstAirDate = firstAirDate,
            genreIds = genreIds,
            originCountry = originCountry,
            originalLanguage = originalLanguage,
            originalName = originalName,
            popularity = popularity,
            posterPath = posterPath,
            voteAverage = voteAverage,
            voteCount = voteCount,
            releaseDate = releaseDate,
            profilePath = profilePath,
            knownFor = knownFor?.let { knownForMapper.map(it) }
        )
    }
}

class KnownForMapper @Inject constructor() {

    fun map(input: List<KnownForDto>): List<KnownFor> = input.map { dto -> map(dto) }

    private fun map(input: KnownForDto): KnownFor = with(input) {
        KnownFor(
            id = id,
            name = name,
            title = title,
            backdropPath = backdropPath,
            firstAirDate = firstAirDate,
            genreIds = genreIds,
            releaseDate = releaseDate,
            mediaType = mediaType,
            originCountry = originCountry,
            originalLanguage = originalLanguage,
            originalName = originalName,
            overview = overview,
            posterPath = posterPath,
            voteAverage = voteAverage,
            voteCount = voteCount
        )
    }
}