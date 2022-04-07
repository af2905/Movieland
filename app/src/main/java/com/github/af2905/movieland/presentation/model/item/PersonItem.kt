package com.github.af2905.movieland.presentation.model.item

import com.github.af2905.movieland.helper.extension.getFullPathToImage

data class PersonItem(
    val id: Int,
    val name: String,
    val birthday: String,
    val knownForDepartment: String,
    val deathday: String?,
    val alsoKnownAs: List<String>?,
    val gender: Int,
    val popularity: Double,
    val biography: String,
    val placeOfBirth: String?,
    val adult: Boolean,
    val imdbId: String,
    val homepage: String?
) {

    var profilePath: String? = null
        get() = field.getFullPathToImage()
}


