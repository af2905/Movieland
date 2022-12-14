package com.github.af2905.movieland.core.data.dto.people

import com.google.gson.annotations.SerializedName

data class PersonPopularDto(
    @SerializedName("page") val page: Int? = null,
    @SerializedName("results") val results: List<PersonPopularResultDto>,
    @SerializedName("total_pages") val totalPages: Int? = null,
    @SerializedName("total_results") val totalResults: Int? = null
)

data class PersonPopularResultDto(
    @SerializedName("id") val id: Int,
    @SerializedName("adult") val adult: Boolean? = null,
    @SerializedName("gender") val gender: Int? = null,
    @SerializedName("known_for") val knownFor: List<KnownForDto>,
    @SerializedName("known_for_department") val knownForDepartment: String? = null,
    @SerializedName("name") val name: String,
    @SerializedName("popularity") val popularity: Double? = null,
    @SerializedName("profile_path") val profilePath: String? = null
)