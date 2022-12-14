package com.github.af2905.movieland.core.data.mapper

import com.github.af2905.movieland.core.common.mapper.ListMapperImpl
import com.github.af2905.movieland.core.common.mapper.Mapper
import com.github.af2905.movieland.core.common.model.item.SearchMultiItem
import com.github.af2905.movieland.core.data.database.entity.plain.KnownFor
import com.github.af2905.movieland.core.data.database.entity.plain.SearchMulti
import com.github.af2905.movieland.core.data.dto.people.KnownForDto
import com.github.af2905.movieland.core.data.dto.search.SearchMultiResultDto
import javax.inject.Inject

class SearchMultiDtoToSearchMultiListMapper @Inject constructor(
    mapper: SearchMultiDtoToSearchMultiMapper
) : ListMapperImpl<SearchMultiResultDto, SearchMulti>(mapper)

class SearchMultiDtoToSearchMultiMapper @Inject constructor(
    private val mapper: KnownForDtoToKnownForListMapper
) : Mapper<SearchMultiResultDto, SearchMulti> {
    override fun map(input: SearchMultiResultDto): SearchMulti = with(input) {
        SearchMulti(
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
            knownFor = knownFor?.let { mapper.map(knownFor) }
        )
    }
}

class SearchMultiToSearchMultiItemListMapper @Inject constructor(
    mapper: SearchMultiToSearchMultiItemMapper
) : ListMapperImpl<SearchMulti, SearchMultiItem>(mapper)

class SearchMultiToSearchMultiItemMapper @Inject constructor() :
    Mapper<SearchMulti, SearchMultiItem> {
    override fun map(input: SearchMulti): SearchMultiItem = with(input) {
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
            knowFor = knownFor
        )
    }
}

class KnownForDtoToKnownForListMapper @Inject constructor(
    mapper: KnownForDtoToKnownForMapper
) : ListMapperImpl<KnownForDto, KnownFor>(mapper)

class KnownForDtoToKnownForMapper @Inject constructor() : Mapper<KnownForDto, KnownFor> {
    override fun map(input: KnownForDto): KnownFor = with(input) {
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