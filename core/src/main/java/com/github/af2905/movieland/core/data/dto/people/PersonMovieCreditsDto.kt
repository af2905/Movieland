package com.github.af2905.movieland.core.data.dto.people

import com.google.gson.annotations.SerializedName

data class PersonMovieCreditsDto(
    @SerializedName("cast") val cast: List<CastDto>,
    @SerializedName("crew") val crew: List<CrewDto>,
    @SerializedName("id") val id: Int
)

data class CastDto(
    @SerializedName("id") val id: Int,
    @SerializedName("adult") val adult: Boolean? = null,
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("original_language") val originalLanguage: String? = null,
    @SerializedName("original_title") val originalTitle: String? = null,
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("popularity") val popularity: Double? = null,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("video") val video: Boolean? = null,
    @SerializedName("vote_average") val voteAverage: Double? = null,
    @SerializedName("vote_count") val voteCount: Int? = null,
    @SerializedName("character") val character: String? = null,
    @SerializedName("credit_id") val creditId: String? = null,
    @SerializedName("order") val order: Int? = null
)

data class CrewDto(
    @SerializedName("id") val id: Int,
    @SerializedName("adult") val adult: Boolean?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_title") val originalTitle: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("video") val video: Boolean?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Int?,
    @SerializedName("credit_id") val creditId: String?,
    @SerializedName("department") val department: String?,
    @SerializedName("job") val job: String? = null

)