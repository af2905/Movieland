package com.github.af2905.movieland.core.data

import com.github.af2905.movieland.util.extension.getFullPathToImage

data class PersonItem(
    val id: Int,
    val name: String,
    val birthday: String?,
    val knownForDepartment: String,
    val deathday: String?,
    val alsoKnownAs: List<String>?,
    val gender: Int,
    val popularity: Double,
    val biography: String,
    val placeOfBirth: String?,
    val adult: Boolean,
    val homepage: String?,
    val liked: Boolean = false
) {

    var profilePath: String? = null
        get() = field.getFullPathToImage()
}