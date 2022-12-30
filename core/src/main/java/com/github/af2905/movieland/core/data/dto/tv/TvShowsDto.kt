package com.github.af2905.movieland.core.data.dto.tv

import com.google.gson.annotations.SerializedName

data class TvShowsDto(
    @SerializedName("page") val page: Int?,
    @SerializedName("results") val tvShows: List<TvShowDto>,
    @SerializedName("total_results") val totalResults: Int?,
    @SerializedName("total_pages") val totalPages: Int?
)

data class TvShowDto(
    @SerializedName("id") val id: Int,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("first_air_date") val firstAirDate: String?,
    @SerializedName("origin_country") val originCountry: List<String>,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("vote_count") val voteCount: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("original_name") val originalName: String?
)