package com.github.af2905.movieland.core.common.model.item

import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.Model

data class MovieV2Item(
    val movieItem: MovieItem,
    override val id: Int = movieItem.id
) : Model(VIEW_TYPE) {

    val titleWithReleaseYear = StringBuilder().apply {
        append(movieItem.title)
        append(SPACE)
        append(LEFT_BRACKET)
        append(movieItem.releaseYear)
        append(RIGHT_BRACKET)
    }

    companion object {
        val VIEW_TYPE = R.layout.list_item_movie_v2

        private const val SPACE = " "
        private const val LEFT_BRACKET = "("
        private const val RIGHT_BRACKET = ")"
    }

    fun interface Listener : ItemDelegate.Listener {
        fun onItemClicked(item: MovieItem)
    }
}