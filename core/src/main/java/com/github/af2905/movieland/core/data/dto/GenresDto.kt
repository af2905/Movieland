package com.github.af2905.movieland.core.data.dto

import com.google.gson.annotations.SerializedName

data class GenresResponseDto(
    @SerializedName("genres")
    val genres: List<GenresDto>
)

data class GenresDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)