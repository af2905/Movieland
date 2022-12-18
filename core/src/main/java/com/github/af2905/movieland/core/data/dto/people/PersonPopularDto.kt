package com.github.af2905.movieland.core.data.dto.people

import com.google.gson.annotations.SerializedName

data class PersonPopularDto(
    @SerializedName("page") val page: Int?,
    @SerializedName("results") val results: List<PersonPopularResultDto>,
    @SerializedName("total_pages") val totalPages: Int?,
    @SerializedName("total_results") val totalResults: Int?
)

data class PersonPopularResultDto(
    @SerializedName("id") val id: Int,
    @SerializedName("adult") val adult: Boolean?,
    @SerializedName("gender") val gender: Int?,
    @SerializedName("known_for") val knownFor: List<KnownForDto>?,
    @SerializedName("known_for_department") val knownForDepartment: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("profile_path") val profilePath: String?
)