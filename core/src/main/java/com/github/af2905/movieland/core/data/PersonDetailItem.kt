package com.github.af2905.movieland.core.data

import com.github.af2905.movieland.util.extension.getFullPathToImage

data class PersonDetailItem(
    val id: Int,
    val name: String,
    val birthday: String?,
    val knownForDepartment: String,
    val deathDay: String?,
    val gender: Int,
    val popularity: Double,
    val biography: String,
    val placeOfBirth: String?,
    val adult: Boolean,
    val homepage: String?,
    val profilePath: String?,
    val liked: Boolean = false
) {

    var profileFullPathToImage: String? = null
        get() = field.getFullPathToImage()
}