package com.github.af2905.movieland.core.data.dto.people

import com.google.gson.annotations.SerializedName

data class PersonDetailDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("birthday") val birthday: String?,
    @SerializedName("known_for_department") val knownForDepartment: String,
    @SerializedName("deathday") val deathDay: String?,
    @SerializedName("gender") val gender: Int,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("biography") val biography: String,
    @SerializedName("place_of_birth") val placeOfBirth: String?,
    @SerializedName("profile_path") val profilePath: String?,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("homepage") val homepage: String?
)