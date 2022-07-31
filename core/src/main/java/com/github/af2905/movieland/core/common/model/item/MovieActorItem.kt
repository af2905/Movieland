package com.github.af2905.movieland.core.common.model.item

import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.Model
import com.github.af2905.movieland.util.extension.getFullPathToImage

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
        val VIEW_TYPE = R.layout.list_item_movie_actor

    }

    fun interface Listener : ItemDelegate.Listener {
        fun onItemClicked(item: MovieActorItem, position: Int)
    }
}