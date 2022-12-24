package com.github.af2905.movieland.core.common.model.item

import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.Model
import com.github.af2905.movieland.util.extension.getFullPathToImage

private const val SEPARATOR = " | "
private const val PERSON_KNOWN_TAKE_DEFAULT = 4

data class PersonItem(
    override val id: Int,
    val name: String?,
    val profilePath: String?,
    val personMovieCreditsCasts: List<PersonMovieCreditsCastItem>
) : Model(VIEW_TYPE) {

    val personKnowFor = personMovieCreditsCasts.filter { !it.title.isNullOrEmpty() }
        .map { it.title }
        .take(PERSON_KNOWN_TAKE_DEFAULT)
        .joinToString(SEPARATOR)

    val profileFullPathToImage: String?
        get() = profilePath.getFullPathToImage()

    companion object {
        val VIEW_TYPE = R.layout.list_item_person
    }

    fun interface Listener : ItemDelegate.Listener {
        fun onItemClicked(item: PersonItem)
    }
}