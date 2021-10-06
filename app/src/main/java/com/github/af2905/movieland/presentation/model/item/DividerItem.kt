package com.github.af2905.movieland.presentation.model.item

import com.github.af2905.movieland.R
import com.github.af2905.movieland.presentation.model.ItemIds.DIVIDER_ITEM_ID
import com.github.af2905.movieland.presentation.model.Model

data class DividerItem(override val id: Int = DIVIDER_ITEM_ID) : Model(VIEW_TYPE) {
    companion object {
        const val VIEW_TYPE = R.layout.list_item_divider
    }
}