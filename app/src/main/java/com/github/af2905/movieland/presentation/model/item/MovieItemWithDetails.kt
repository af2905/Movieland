package com.github.af2905.movieland.presentation.model.item

import com.github.af2905.movieland.R
import com.github.af2905.movieland.presentation.common.ItemDelegate
import com.github.af2905.movieland.presentation.feature.detail.moviedetail.item.MovieDetailsItem
import com.github.af2905.movieland.presentation.model.Model

data class MovieItemWithDetails(
    val movieDetailsItem: MovieDetailsItem,
    override val id: Int = movieDetailsItem.id
) : Model(VIEW_TYPE) {

    companion object {
        const val VIEW_TYPE = R.layout.list_item_movie_with_details
    }

    fun interface Listener : ItemDelegate.Listener {
        fun onItemClicked(item: MovieDetailsItem, position: Int)
    }
}