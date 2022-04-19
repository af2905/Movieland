package com.github.af2905.movieland.data.dto.people

import com.google.gson.annotations.SerializedName

data class PersonDetailResponseDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("birthday") val birthday: String?,
    @SerializedName("known_for_department") val knownForDepartment: String,
    @SerializedName("deathday") val deathday: String?,
    @SerializedName("also_known_as") val alsoKnownAs: List<String>?,
    @SerializedName("gender") val gender: Int,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("biography") val biography: String,
    @SerializedName("place_of_birth") val placeOfBirth: String?,
    @SerializedName("profile_path") val profilePath: String?,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("homepage") val homepage: String?
)