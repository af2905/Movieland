package com.github.af2905.movieland.presentation.feature.home.item

import com.github.af2905.movieland.R
import com.github.af2905.movieland.core.common.model.ItemIds.SHIMMER_ITEM_ID
import com.github.af2905.movieland.core.common.model.Model

data class ListMovieVariantPlaceholderItem(
    override val id: Int = SHIMMER_ITEM_ID
) : Model(VIEW_TYPE) {

    companion object {
        const val VIEW_TYPE = R.layout.list_item_movie_variant_placeholder
    }
}