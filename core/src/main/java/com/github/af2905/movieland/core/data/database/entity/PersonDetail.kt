package com.github.af2905.movieland.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PersonDetail(
    @PrimaryKey val id: Int,
    val name: String,
    val birthday: String?,
    val knownForDepartment: String,
    val deathDay: String?,
    val gender: Int,
    val popularity: Double,
    val biography: String,
    val placeOfBirth: String?,
    val profilePath: String?,
    val adult: Boolean,
    val homepage: String?
)