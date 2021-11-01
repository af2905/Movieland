package com.github.af2905.movieland.presentation.model.item

import com.github.af2905.movieland.R
import com.github.af2905.movieland.presentation.common.ItemDelegate
import com.github.af2905.movieland.presentation.model.Model

data class MovieItemVariant(val movieItem: MovieItem, override val id: Int = movieItem.id) :
    Model(VIEW_TYPE) {

    companion object {
        const val VIEW_TYPE = R.layout.list_item_movie_variant
    }

    fun interface Listener : ItemDelegate.Listener {
        fun onItemClicked(item: MovieItem, position: Int)
    }
}