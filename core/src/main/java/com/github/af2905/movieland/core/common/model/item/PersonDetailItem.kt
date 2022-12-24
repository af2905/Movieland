package com.github.af2905.movieland.core.common.model.item

import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.Model
import com.github.af2905.movieland.util.extension.getFullPathToImage

data class PersonDetailItem(
    override val id: Int,
    val name: String?,
    val birthday: String?,
    val knownForDepartment: String?,
    val deathDay: String?,
    val gender: Int?,
    val popularity: Double?,
    val biography: String?,
    val placeOfBirth: String?,
    val adult: Boolean?,
    val homepage: String?,
    val profilePath: String?,
    val personMovieCreditsCasts: List<PersonMovieCreditsCastItem> = emptyList(),
    val liked: Boolean = false
) : Model(VIEW_TYPE) {

    val profileFullPathToImage: String?
        get() = profilePath.getFullPathToImage()

    val popularityStringValue = popularity.toString()

    val birthdayTitle: Int = R.string.birthday_title
    val deathDayTitle: Int = R.string.deathday_title
    val placeOfBirthTitle = R.string.place_of_birth_title
    val biographyTitle = R.string.biography_title
    val popularityTitle = R.string.popularity_title

    val birthdayVisible = birthday.isNullOrEmpty().not()
    val deathDayVisible = deathDay.isNullOrEmpty().not()
    val placeOfBirthVisible = placeOfBirth.isNullOrEmpty().not()
    val biographyVisible = biography.isNullOrEmpty().not()
    val popularityVisible = popularityStringValue.isEmpty().not()

    fun interface Listener : ItemDelegate.Listener {
        fun onLikedClick(item: PersonDetailItem)
    }

    companion object {
        val VIEW_TYPE = R.layout.list_item_person_detail

        fun PersonDetailItem.mapToPersonItem() = with(this) {
            PersonItem(
                id = id,
                name = name,
                profilePath = profilePath,
                personMovieCreditsCasts = personMovieCreditsCasts
            )
        }
    }
}