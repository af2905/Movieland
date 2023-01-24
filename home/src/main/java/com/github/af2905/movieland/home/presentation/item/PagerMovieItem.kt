package com.github.af2905.movieland.home.presentation.item

import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.Model
import com.github.af2905.movieland.core.common.model.item.MovieItem
import com.github.af2905.movieland.home.R

data class PagerMovieItem(
    val movieItem: MovieItem,
    override val id: Int = movieItem.id,
) : Model(VIEW_TYPE) {

    companion object {
        val VIEW_TYPE = R.layout.list_item_pager_movie
    }

    fun interface Listener : ItemDelegate.Listener {
        fun onItemClicked(item: MovieItem)
    }
}