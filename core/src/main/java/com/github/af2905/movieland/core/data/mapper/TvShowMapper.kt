package com.github.af2905.movieland.core.data.mapper

import com.github.af2905.movieland.core.data.database.entity.TvShow
import com.github.af2905.movieland.core.data.database.entity.TvShowType
import com.github.af2905.movieland.core.data.dto.tv.TvShowDto
import javax.inject.Inject

class TvShowMapper @Inject constructor() {

    fun map(input: List<TvShowDto>): List<TvShow> = input.map { dto -> map(dto) }

    fun map(input: List<TvShowDto>, type: TvShowType, timeStamp: Long): List<TvShow> =
        input.map { dto -> map(dto, type, timeStamp) }

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

    private fun map(input: TvShowDto, type: TvShowType, timeStamp: Long): TvShow = with(input) {
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
}