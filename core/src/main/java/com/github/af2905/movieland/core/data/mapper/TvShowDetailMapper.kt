package com.github.af2905.movieland.core.data.mapper

import com.github.af2905.movieland.core.data.database.entity.CreatedBy
import com.github.af2905.movieland.core.data.database.entity.Genre
import com.github.af2905.movieland.core.data.database.entity.GenreType
import com.github.af2905.movieland.core.data.database.entity.LastEpisodeToAir
import com.github.af2905.movieland.core.data.database.entity.Network
import com.github.af2905.movieland.core.data.database.entity.Season
import com.github.af2905.movieland.core.data.database.entity.TvShowDetail
import com.github.af2905.movieland.core.data.dto.tv.CreatedByDto
import com.github.af2905.movieland.core.data.dto.tv.LastEpisodeToAirDto
import com.github.af2905.movieland.core.data.dto.tv.NetworkDto
import com.github.af2905.movieland.core.data.dto.tv.SeasonDto
import com.github.af2905.movieland.core.data.dto.tv.TvShowDetailDto
import javax.inject.Inject

class TvShowDetailMapper @Inject constructor() {

    fun map(dto: TvShowDetailDto): TvShowDetail {
        return TvShowDetail(
            id = dto.id,
            backdropPath = dto.backdropPath,
            createdBy = dto.createdBy?.map { mapCreatedBy(it) } ?: emptyList(),
            episodeRunTime = dto.episodeRunTime ?: emptyList(),
            firstAirDate = dto.firstAirDate,
            genre = dto.genres?.map { Genre(it.id, it.name.orEmpty(), GenreType.TV_SHOW) } ?: emptyList(),
            homepage = dto.homepage,
            inProduction = dto.inProduction,
            languages = dto.languages,
            lastAirDate = dto.lastAirDate,
            lastEpisodeToAir = dto.lastEpisodeToAir?.let { mapLastEpisodeToAir(it) },
            name = dto.name,
            networks = dto.networks?.map { mapNetwork(it) } ?: emptyList(),
            numberOfEpisodes = dto.numberOfEpisodes,
            numberOfSeasons = dto.numberOfSeasons,
            originCountry = dto.originCountry ?: emptyList(),
            originalLanguage = dto.originalLanguage,
            originalName = dto.originalName,
            overview = dto.overview,
            popularity = dto.popularity,
            posterPath = dto.posterPath,
            productionCompanies = emptyList(),
            productionCountries = emptyList(),
            seasons = dto.seasons?.map { mapSeason(it) } ?: emptyList(),
            status = dto.status,
            tagline = dto.tagline,
            type = dto.type,
            voteAverage = dto.voteAverage,
            voteCount = dto.voteCount,
            similarTvShows = emptyList(),
            creditsCasts = emptyList(),
            liked = false
        )
    }

    private fun mapCreatedBy(dto: CreatedByDto): CreatedBy {
        return CreatedBy(
            id = dto.id,
            creditId = dto.creditId,
            name = dto.name,
            gender = dto.gender,
            profilePath = dto.profilePath
        )
    }

    private fun mapLastEpisodeToAir(dto: LastEpisodeToAirDto): LastEpisodeToAir {
        return LastEpisodeToAir(
            id = dto.id,
            airDate = dto.airDate,
            episodeNumber = dto.episodeNumber,
            name = dto.name,
            overview = dto.overview,
            productionCode = dto.productionCode,
            seasonNumber = dto.seasonNumber,
            stillPath = dto.stillPath,
            voteAverage = dto.voteAverage,
            voteCount = dto.voteCount
        )
    }

    private fun mapNetwork(dto: NetworkDto): Network {
        return Network(
            id = dto.id,
            name = dto.name,
            logoPath = dto.logoPath,
            originCountry = dto.originCountry
        )
    }

    private fun mapSeason(dto: SeasonDto): Season {
        return Season(
            id = dto.id,
            airDate = dto.airDate,
            episodeCount = dto.episodeCount,
            name = dto.name,
            overview = dto.overview,
            posterPath = dto.posterPath,
            seasonNumber = dto.seasonNumber
        )
    }
}
