package com.github.af2905.movieland.presentation.model.item

import com.github.af2905.movieland.R
import com.github.af2905.movieland.presentation.common.ItemDelegate
import com.github.af2905.movieland.presentation.model.Model

data class MovieItemVariant(val movieItem: MovieItem, override val id: Int = movieItem.id) :
    Model(VIEW_TYPE) {

    val titleWithReleaseYear = StringBuilder().apply {
        append(movieItem.title)
        append(SPACE)
        append(LEFT_BRACKET)
        append(movieItem.releaseYear)
        append(RIGHT_BRACKET)
    }

    companion object {
        const val VIEW_TYPE = R.layout.list_item_movie_variant

        private const val SPACE = " "
        private const val LEFT_BRACKET = "("
        private const val RIGHT_BRACKET = ")"
    }

    fun interface Listener : ItemDelegate.Listener {
        fun onItemClicked(item: MovieItem, position: Int)
    }
}