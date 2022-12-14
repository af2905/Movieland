package com.github.af2905.movieland.core.data.dto.people

import com.google.gson.annotations.SerializedName

data class KnownForDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    @SerializedName("first_air_date") val firstAirDate: String? = null,
    @SerializedName("genre_ids") val genreIds: List<Int>?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("media_type") val mediaType: String,
    @SerializedName("origin_country") val originCountry: ArrayList<String> = arrayListOf(),
    @SerializedName("original_language") val originalLanguage: String? = null,
    @SerializedName("original_name") val originalName: String? = null,
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("vote_average") val voteAverage: Double? = null,
    @SerializedName("vote_count") val voteCount: Int? = null
)