package com.github.af2905.movieland.home.presentation.item

import com.github.af2905.movieland.core.common.model.ItemIds.SHIMMER_ITEM_ID
import com.github.af2905.movieland.core.common.model.Model
import com.github.af2905.movieland.home.R

data class HomePlaceholderItem(override val id: Int = SHIMMER_ITEM_ID) : Model(VIEW_TYPE) {

    companion object {
        val VIEW_TYPE = R.layout.list_item_home_placeholder
    }
}