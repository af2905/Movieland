package com.github.af2905.movieland.core.common.model.item

import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.model.ItemIds.EMPTY_RESULT_ITEM_ID
import com.github.af2905.movieland.core.common.model.Model

data class EmptyResultItem(override val id: Int = EMPTY_RESULT_ITEM_ID) : Model(VIEW_TYPE) {

    companion object {
        val VIEW_TYPE = R.layout.list_item_empty_result
    }
}