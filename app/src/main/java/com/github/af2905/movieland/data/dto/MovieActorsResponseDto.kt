package com.github.af2905.movieland.data.dto

import com.google.gson.annotations.SerializedName

data class MovieActorsResponseDto(
    @SerializedName("id") val id: Int,
    @SerializedName("cast") val actors: List<MovieActorDto>
)

data class MovieActorDto(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("gender") val gender: Int? = null,
    @SerializedName("id") val id: Int,
    @SerializedName("known_for_department") val knownForDepartment: String?,
    @SerializedName("name") val name: String,
    @SerializedName("original_name") val originalName: String?,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("profile_path") val profilePath: String?,
    @SerializedName("cast_id") val castId: Int?,
    @SerializedName("character") val character: String?,
    @SerializedName("credit_id") val creditId: String?,
    @SerializedName("order") val order: Int?
)