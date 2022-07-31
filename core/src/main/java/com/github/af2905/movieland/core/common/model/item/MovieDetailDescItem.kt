package com.github.af2905.movieland.core.common.model.item

import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.model.Model

data class MovieDetailDescItem(val movieDetailsItem: MovieDetailsItem) : Model(VIEW_TYPE) {

    override val id: Int
        get() = movieDetailsItem.id

    companion object {
        val VIEW_TYPE = R.layout.list_item_movie_detail_desc
    }
}