package com.github.af2905.movieland.core.data.database.entity.plain

data class MovieActor(
    val id: Int,
    val adult: Boolean,
    val gender: Int? = null,
    val knownForDepartment: String?,
    val name: String,
    val originalName: String?,
    val popularity: Double,
    val castId: Int?,
    val character: String?,
    val creditId: String?,
    val order: Int?,
    var profilePath: String?
)