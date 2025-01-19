package com.github.af2905.movieland.core.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.af2905.movieland.core.data.database.entity.plain.CreditsCast

private const val LAST_EPISODE_TO_AIR_ID = "last_episode_to_air_id"
private const val LAST_EPISODE_TO_AIR_DATE = "last_episode_to_air_date"
private const val LAST_EPISODE_TO_AIR_EPISODE_NUMBER = "last_episode_to_air_episode_number"
private const val LAST_EPISODE_TO_AIR_NAME = "last_episode_to_air_name"
private const val LAST_EPISODE_TO_AIR_OVERVIEW = "last_episode_to_air_overview"
private const val LAST_EPISODE_TO_AIR_PRODUCTION_CODE = "last_episode_to_air_production_code"
private const val LAST_EPISODE_TO_AIR_SEASON_NUMBER = "last_episode_to_air_season_number"
private const val LAST_EPISODE_TO_AIR_SEASON_STILL_PATH = "last_episode_to_air_still_path"
private const val LAST_EPISODE_TO_AIR_VOTE_AVERAGE = "last_episode_to_air_vote_average"
private const val LAST_EPISODE_TO_AIR_VOTE_COUNT = "last_episode_to_air_vote_count"

@Entity
data class TvShowDetail(
    @PrimaryKey val id: Int,
    @Embedded val lastEpisodeToAir: LastEpisodeToAir?,
    val backdropPath: String?,
    val createdBy: List<CreatedBy>?,
    val episodeRunTime: List<Int>?,
    val firstAirDate: String?,
    val genre: List<Genre>?,
    val homepage: String?,
    val inProduction: Boolean?,
    val languages: List<String>,
    val lastAirDate: String?,
    val name: String?,
    val networks: List<Network>?,
    val numberOfEpisodes: Int?,
    val numberOfSeasons: Int?,
    val originCountry: List<String>?,
    val originalLanguage: String?,
    val originalName: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val productionCompanies: List<ProductionCompany>?,
    val productionCountries: List<ProductionCountry>?,
    val seasons: List<Season>?,
    val status: String?,
    val tagline: String?,
    val type: String?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val liked: Boolean,
    val creditsCasts: List<CreditsCast>,
    val similarTvShows: List<TvShow>
)

data class LastEpisodeToAir(
    @ColumnInfo(name = LAST_EPISODE_TO_AIR_ID) val id: Int,
    @ColumnInfo(name = LAST_EPISODE_TO_AIR_DATE) val airDate: String?,
    @ColumnInfo(name = LAST_EPISODE_TO_AIR_EPISODE_NUMBER) val episodeNumber: Int?,
    @ColumnInfo(name = LAST_EPISODE_TO_AIR_NAME) val name: String?,
    @ColumnInfo(name = LAST_EPISODE_TO_AIR_OVERVIEW) val overview: String?,
    @ColumnInfo(name = LAST_EPISODE_TO_AIR_PRODUCTION_CODE) val productionCode: String?,
    @ColumnInfo(name = LAST_EPISODE_TO_AIR_SEASON_NUMBER) val seasonNumber: Int?,
    @ColumnInfo(name = LAST_EPISODE_TO_AIR_SEASON_STILL_PATH) val stillPath: String?,
    @ColumnInfo(name = LAST_EPISODE_TO_AIR_VOTE_AVERAGE) val voteAverage: Double?,
    @ColumnInfo(name = LAST_EPISODE_TO_AIR_VOTE_COUNT) val voteCount: Int?
)

data class CreatedBy(
    val id: Int,
    val creditId: String?,
    val name: String?,
    val gender: Int?,
    val profilePath: String?
)

data class Network(
    val id: Int,
    val name: String?,
    val logoPath: String?,
    val originCountry: String?
)

data class Season(
    val id: Int,
    val airDate: String?,
    val episodeCount: Int?,
    val name: String?,
    val overview: String?,
    val posterPath: String?,
    val seasonNumber: Int?
)