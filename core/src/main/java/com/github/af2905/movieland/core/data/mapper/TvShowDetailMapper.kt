package com.github.af2905.movieland.core.data.mapper

import com.github.af2905.movieland.core.common.model.item.TvShowDetailItem
import com.github.af2905.movieland.core.data.database.entity.*
import com.github.af2905.movieland.core.data.dto.tv.*
import com.github.af2905.movieland.util.extension.fiveStarRating
import javax.inject.Inject
import com.github.af2905.movieland.core.common.model.item.CreatedBy as UiCreatedBy
import com.github.af2905.movieland.core.common.model.item.LastEpisodeToAir as UiLastEpisodeToAir
import com.github.af2905.movieland.core.common.model.item.Network as UiNetwork
import com.github.af2905.movieland.core.common.model.item.Season as UiSeason

class TvShowDetailMapper @Inject constructor(
    private val lastEpisodeToAirMapper: LastEpisodeToAirMapper,
    private val createdByMapper: CreatedByMapper,
    private val networkMapper: NetworkMapper,
    private val seasonMapper: SeasonMapper,
    private val creditsCastMapper: CreditsCastMapper,
    private val genreMapper: GenreMapper,
    private val productionCompanyMapper: ProductionCompanyMapper,
    private val productionCountryMapper: ProductionCountryMapper,
    private val tvShowMapper: TvShowMapper
) {
    @JvmName(DTO_TO_UI_ITEM_MAPPER)
    fun map(input: TvShowDetailDto): TvShowDetailItem = with(input) {
        TvShowDetailItem(
            id = id,
            lastEpisodeToAir = lastEpisodeToAir?.let { lastEpisodeToAirMapper.map(it) },
            backdropPath = backdropPath,
            createdBy = createdBy?.let { createdByMapper.map(it) },
            episodeRunTime = episodeRunTime,
            firstAirDate = firstAirDate,
            genres = genres?.let { genreMapper.map(it) },
            homepage = homepage,
            inProduction = inProduction,
            languages = languages,
            lastAirDate = lastAirDate,
            name = name,
            networks = networks?.let { networkMapper.map(it) },
            numberOfEpisodes = numberOfEpisodes,
            numberOfSeasons = numberOfSeasons,
            originCountry = originCountry,
            originalLanguage = originalLanguage,
            originalName = originalName,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            productionCompanies = productionCompanies?.let { productionCompanyMapper.map(it) },
            productionCountries = productionCountries?.let { productionCountryMapper.map(it) },
            seasons = seasons?.let { seasonMapper.map(it) },
            status = status,
            tagline = tagline,
            type = type,
            voteAverage = voteAverage,
            voteCount = voteCount,
            voteAverageStar = voteAverage?.fiveStarRating()?.toFloat()
        )
    }

    @JvmName(ENTITY_TO_UI_ITEM_MAPPER)
    fun map(input: TvShowDetail): TvShowDetailItem = with(input) {
        TvShowDetailItem(
            id = id,
            lastEpisodeToAir = lastEpisodeToAir?.let { lastEpisodeToAirMapper.map(it) },
            backdropPath = backdropPath,
            createdBy = createdBy?.let { createdByMapper.map(it) },
            episodeRunTime = episodeRunTime,
            firstAirDate = firstAirDate,
            genres = genres?.let { genreMapper.map(it) },
            homepage = homepage,
            inProduction = inProduction,
            languages = languages,
            lastAirDate = lastAirDate,
            name = name,
            networks = networks?.let { networkMapper.map(it) },
            numberOfEpisodes = numberOfEpisodes,
            numberOfSeasons = numberOfSeasons,
            originCountry = originCountry,
            originalLanguage = originalLanguage,
            originalName = originalName,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            productionCompanies = productionCompanies?.let { productionCompanyMapper.map(it) },
            productionCountries = productionCountries?.let { productionCountryMapper.map(it) },
            seasons = seasons?.let { seasonMapper.map(it) },
            status = status,
            tagline = tagline,
            type = type,
            voteAverage = voteAverage,
            voteAverageStar = voteAverage?.fiveStarRating()?.toFloat(),
            voteCount = voteCount,
            liked = liked,
            creditsCasts = creditsCasts.let { creditsCastMapper.map(it) },
            similarTvShows = tvShowMapper.map(similarTvShows)
        )
    }

    @JvmName(UI_ITEM_TO_ENTITY_MAPPER)
    fun map(input: TvShowDetailItem): TvShowDetail = with(input) {
        TvShowDetail(
            id = id,
            lastEpisodeToAir = lastEpisodeToAir?.let { lastEpisodeToAirMapper.map(it) },
            backdropPath = backdropPath,
            createdBy = createdBy?.let { createdByMapper.map(it) },
            episodeRunTime = episodeRunTime,
            firstAirDate = firstAirDate,
            genres = genres?.let { genreMapper.map(it) },
            homepage = homepage,
            inProduction = inProduction,
            languages = languages,
            lastAirDate = lastAirDate,
            name = name,
            networks = networks?.let { networkMapper.map(it) },
            numberOfEpisodes = numberOfEpisodes,
            numberOfSeasons = numberOfSeasons,
            originCountry = originCountry,
            originalLanguage = originalLanguage,
            originalName = originalName,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            productionCompanies = productionCompanies?.let { productionCompanyMapper.map(it) },
            productionCountries = productionCountries?.let { productionCountryMapper.map(it) },
            seasons = seasons?.let { seasonMapper.map(it) },
            status = status,
            tagline = tagline,
            type = type,
            voteAverage = voteAverage,
            voteCount = voteCount,
            liked = liked,
            creditsCasts = creditsCasts.let { creditsCastMapper.map(it) },
            similarTvShows = tvShowMapper.map(similarTvShows)
        )
    }
}

class LastEpisodeToAirMapper @Inject constructor() {
    @JvmName(DTO_TO_UI_ITEM_MAPPER)
    fun map(input: LastEpisodeToAirDto): UiLastEpisodeToAir = with(input) {
        UiLastEpisodeToAir(
            id = id,
            airDate = airDate,
            episodeNumber = episodeNumber,
            name = name,
            overview = overview,
            productionCode = productionCode,
            seasonNumber = seasonNumber,
            stillPath = stillPath,
            voteAverage = voteAverage,
            voteCount = voteCount
        )
    }

    @JvmName(ENTITY_TO_UI_ITEM_MAPPER)
    fun map(input: LastEpisodeToAir): UiLastEpisodeToAir = with(input) {
        UiLastEpisodeToAir(
            id = id,
            airDate = airDate,
            episodeNumber = episodeNumber,
            name = name,
            overview = overview,
            productionCode = productionCode,
            seasonNumber = seasonNumber,
            stillPath = stillPath,
            voteAverage = voteAverage,
            voteCount = voteCount
        )
    }

    @JvmName(UI_ITEM_TO_ENTITY_MAPPER)
    fun map(input: UiLastEpisodeToAir): LastEpisodeToAir = with(input) {
        LastEpisodeToAir(
            id = id,
            airDate = airDate,
            episodeNumber = episodeNumber,
            name = name,
            overview = overview,
            productionCode = productionCode,
            seasonNumber = seasonNumber,
            stillPath = stillPath,
            voteAverage = voteAverage,
            voteCount = voteCount
        )
    }
}

class CreatedByMapper @Inject constructor() {
    @JvmName(DTO_TO_UI_ITEM_MAPPER)
    fun map(input: List<CreatedByDto>): List<UiCreatedBy> = input.map { dto -> map(dto) }

    @JvmName(ENTITY_TO_UI_ITEM_MAPPER)
    fun map(input: List<CreatedBy>): List<UiCreatedBy> = input.map { entity -> map(entity) }

    @JvmName(UI_ITEM_TO_ENTITY_MAPPER)
    fun map(input: List<UiCreatedBy>): List<CreatedBy> = input.map { item -> map(item) }

    @JvmName(DTO_TO_UI_ITEM_MAPPER)
    private fun map(input: CreatedByDto): UiCreatedBy = with(input) {
        UiCreatedBy(
            id = id,
            creditId = creditId,
            name = name,
            gender = gender,
            profilePath = profilePath
        )
    }

    @JvmName(ENTITY_TO_UI_ITEM_MAPPER)
    fun map(input: CreatedBy): UiCreatedBy = with(input) {
        UiCreatedBy(
            id = id,
            creditId = creditId,
            name = name,
            gender = gender,
            profilePath = profilePath
        )
    }

    @JvmName(UI_ITEM_TO_ENTITY_MAPPER)
    fun map(input: UiCreatedBy): CreatedBy = with(input) {
        CreatedBy(
            id = id,
            creditId = creditId,
            name = name,
            gender = gender,
            profilePath = profilePath
        )
    }
}

class NetworkMapper @Inject constructor() {
    @JvmName(DTO_TO_UI_ITEM_MAPPER)
    fun map(input: List<NetworkDto>): List<UiNetwork> = input.map { dto -> map(dto) }

    @JvmName(ENTITY_TO_UI_ITEM_MAPPER)
    fun map(input: List<Network>): List<UiNetwork> = input.map { entity -> map(entity) }

    @JvmName(UI_ITEM_TO_ENTITY_MAPPER)
    fun map(input: List<UiNetwork>): List<Network> = input.map { item -> map(item) }

    @JvmName(DTO_TO_UI_ITEM_MAPPER)
    private fun map(input: NetworkDto): UiNetwork = with(input) {
        UiNetwork(
            id = id,
            name = name,
            logoPath = logoPath,
            originCountry = originCountry
        )
    }

    @JvmName(ENTITY_TO_UI_ITEM_MAPPER)
    fun map(input: Network): UiNetwork = with(input) {
        UiNetwork(
            id = id,
            name = name,
            logoPath = logoPath,
            originCountry = originCountry
        )
    }

    @JvmName(UI_ITEM_TO_ENTITY_MAPPER)
    fun map(input: UiNetwork): Network = with(input) {
        Network(
            id = id,
            name = name,
            logoPath = logoPath,
            originCountry = originCountry
        )
    }
}

class SeasonMapper @Inject constructor() {
    @JvmName(DTO_TO_UI_ITEM_MAPPER)
    fun map(input: List<SeasonDto>): List<UiSeason> = input.map { dto -> map(dto) }

    @JvmName(ENTITY_TO_UI_ITEM_MAPPER)
    fun map(input: List<Season>): List<UiSeason> = input.map { entity -> map(entity) }

    @JvmName(UI_ITEM_TO_ENTITY_MAPPER)
    fun map(input: List<UiSeason>): List<Season> = input.map { item -> map(item) }

    @JvmName(DTO_TO_UI_ITEM_MAPPER)
    private fun map(input: SeasonDto): UiSeason = with(input) {
        UiSeason(
            id = id,
            airDate = airDate,
            episodeCount = episodeCount,
            name = name,
            overview = overview,
            posterPath = posterPath,
            seasonNumber = seasonNumber
        )
    }

    @JvmName(ENTITY_TO_UI_ITEM_MAPPER)
    fun map(input: Season): UiSeason = with(input) {
        UiSeason(
            id = id,
            airDate = airDate,
            episodeCount = episodeCount,
            name = name,
            overview = overview,
            posterPath = posterPath,
            seasonNumber = seasonNumber
        )
    }

    @JvmName(UI_ITEM_TO_ENTITY_MAPPER)
    fun map(input: UiSeason): Season = with(input) {
        Season(
            id = id,
            airDate = airDate,
            episodeCount = episodeCount,
            name = name,
            overview = overview,
            posterPath = posterPath,
            seasonNumber = seasonNumber
        )
    }
}
