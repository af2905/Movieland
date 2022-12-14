package com.github.af2905.movieland.core.data.dto.search

import com.github.af2905.movieland.core.data.dto.people.KnownForDto
import com.google.gson.annotations.SerializedName

data class SearchMultiDto(
    @SerializedName("results") val results: List<SearchMultiResultDto>,
    @SerializedName("page") val page: Int?,
    @SerializedName("total_pages") val totalPages: Int?,
    @SerializedName("total_results") val totalResults: Int?
)

data class SearchMultiResultDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("media_type") val mediaType: String,
    @SerializedName("overview") val overview: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("first_air_date") val firstAirDate: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>?,
    @SerializedName("origin_country") val originCountry: List<String>?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_name") val originalName: String?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Int?,
    @SerializedName("profile_path") val profilePath: String?,
    @SerializedName("known_for") val knownFor: List<KnownForDto>?
)