package com.github.af2905.movieland.core.data.dto.tv

import com.github.af2905.movieland.core.data.dto.movie.GenreDto
import com.github.af2905.movieland.core.data.dto.movie.ProductionCompanyDto
import com.github.af2905.movieland.core.data.dto.movie.ProductionCountryDto
import com.google.gson.annotations.SerializedName

data class TvShowDetailDto(
    @SerializedName("id") val id: Int,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("created_by") val createdBy: List<CreatedByDto>?,
    @SerializedName("episode_run_time") val episodeRunTime: List<Int>?,
    @SerializedName("first_air_date") val firstAirDate: String?,
    @SerializedName("genres") val genres: List<GenreDto>?,
    @SerializedName("homepage") val homepage: String?,
    @SerializedName("in_production") val inProduction: Boolean?,
    @SerializedName("languages") val languages: List<String>,
    @SerializedName("last_air_date") val lastAirDate: String?,
    @SerializedName("last_episode_to_air") val lastEpisodeToAir: LastEpisodeToAirDto?,
    @SerializedName("name") val name: String?,
    @SerializedName("networks") val networks: List<NetworkDto>?,
    @SerializedName("number_of_episodes") val numberOfEpisodes: Int?,
    @SerializedName("number_of_seasons") val numberOfSeasons: Int?,
    @SerializedName("origin_country") val originCountry: List<String>?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_name") val originalName: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompanyDto>?,
    @SerializedName("production_countries") val productionCountries: List<ProductionCountryDto>?,
    @SerializedName("seasons") val seasons: List<SeasonDto>?,
    @SerializedName("status") val status: String?,
    @SerializedName("tagline") val tagline: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Int?
)

data class CreatedByDto(
    @SerializedName("id") val id: Int,
    @SerializedName("credit_id") val creditId: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("gender") val gender: Int?,
    @SerializedName("profile_path") val profilePath: String?
)

data class LastEpisodeToAirDto(
    @SerializedName("id") val id: Int,
    @SerializedName("air_date") val airDate: String?,
    @SerializedName("episode_number") val episodeNumber: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("production_code") val productionCode: String?,
    @SerializedName("season_number") val seasonNumber: Int?,
    @SerializedName("still_path") val stillPath: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Int?
)

data class NetworkDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("logo_path") val logoPath: String?,
    @SerializedName("origin_country") val originCountry: String?
)

data class SeasonDto(
    @SerializedName("id") val id: Int,
    @SerializedName("air_date") val airDate: String?,
    @SerializedName("episode_count") val episodeCount: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("season_number") val seasonNumber: Int?
)