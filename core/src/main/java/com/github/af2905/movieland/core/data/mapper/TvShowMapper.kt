package com.github.af2905.movieland.core.data.mapper

import com.github.af2905.movieland.core.common.model.item.TvShowItem
import com.github.af2905.movieland.core.data.database.entity.TvShow
import com.github.af2905.movieland.core.data.dto.tv.TvShowDto
import javax.inject.Inject

class TvShowMapper @Inject constructor() {

    @JvmName(DTO_TO_ENTITY_MAPPER)
    fun map(input: List<TvShowDto>): List<TvShow> = input.map { dto -> map(dto) }

    fun map(input: List<TvShowDto>, type: String, timeStamp: Long): List<TvShow> =
        input.map { dto -> map(dto, type, timeStamp) }

    @JvmName(ENTITY_TO_UI_ITEM_MAPPER)
    fun map(input: List<TvShow>): List<TvShowItem> = input.map { entity -> map(entity) }

    @JvmName(UI_ITEM_TO_ENTITY_MAPPER)
    fun map(input: List<TvShowItem>): List<TvShow> = input.map { item -> map(item) }

    private fun map(input: TvShowDto): TvShow = with(input) {
        TvShow(
            id = id,
            posterPath = posterPath,
            popularity = popularity,
            backdropPath = backdropPath,
            voteAverage = voteAverage,
            overview = overview,
            firstAirDate = firstAirDate,
            originCountry = originCountry,
            genreIds = genreIds,
            originalLanguage = originalLanguage,
            voteCount = voteCount,
            name = name,
            originalName = originalName
        )
    }

    private fun map(input: TvShowDto, type: String, timeStamp: Long): TvShow = with(input) {
        TvShow(
            id = id,
            posterPath = posterPath,
            popularity = popularity,
            backdropPath = backdropPath,
            voteAverage = voteAverage,
            overview = overview,
            firstAirDate = firstAirDate,
            originCountry = originCountry,
            genreIds = genreIds,
            originalLanguage = originalLanguage,
            voteCount = voteCount,
            name = name,
            originalName = originalName,
            tvShowType = type,
            timeStamp = timeStamp
        )
    }

    private fun map(input: TvShow): TvShowItem = with(input) {
        TvShowItem(
            id = id,
            posterPath = posterPath,
            popularity = popularity,
            backdropPath = backdropPath,
            voteAverage = voteAverage,
            overview = overview,
            firstAirDate = firstAirDate,
            originCountry = originCountry,
            genreIds = genreIds,
            originalLanguage = originalLanguage,
            voteCount = voteCount,
            name = name,
            originalName = originalName
        )
    }

    private fun map(input: TvShowItem): TvShow = with(input) {
        TvShow(
            id = id,
            posterPath = posterPath,
            popularity = popularity,
            backdropPath = backdropPath,
            voteAverage = voteAverage,
            overview = overview,
            firstAirDate = firstAirDate,
            originCountry = originCountry,
            genreIds = genreIds,
            originalLanguage = originalLanguage,
            voteCount = voteCount,
            name = name,
            originalName = originalName
        )
    }
}