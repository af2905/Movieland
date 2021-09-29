package com.github.af2905.movieland.presentation.model.item

import com.github.af2905.movieland.R
import com.github.af2905.movieland.helper.extension.getFullPathToImage
import com.github.af2905.movieland.presentation.common.ItemDelegate
import com.github.af2905.movieland.presentation.model.Model

data class MovieActorItem(
    override val id: Int,
    val adult: Boolean,
    val gender: Int? = null,
    val knownForDepartment: String?,
    val name: String,
    val originalName: String?,
    val popularity: Double,
    val castId: Int?,
    val character: String?,
    val creditId: String?,
    val order: Int?
) : Model(VIEW_TYPE) {

    var profilePath: String? = null
        get() = field.getFullPathToImage()

    companion object {
        const val VIEW_TYPE = R.layout.list_item_movie_actor

    }

    fun interface Listener : ItemDelegate.Listener {
        fun onItemClicked(item: MovieActorItem, position: Int)
    }
}