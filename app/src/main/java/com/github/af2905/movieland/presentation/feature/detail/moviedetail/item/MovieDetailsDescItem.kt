package com.github.af2905.movieland.presentation.feature.detail.moviedetail.item

import com.github.af2905.movieland.R
import com.github.af2905.movieland.presentation.model.Model

data class MovieDetailsDescItem(val movieDetailsItem: MovieDetailsItem) : Model(VIEW_TYPE) {

    override val id: Int
        get() = movieDetailsItem.id

    companion object {
        const val VIEW_TYPE = R.layout.list_item_movie_details_desc
    }
}