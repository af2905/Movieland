package com.github.af2905.movieland.core.common.model.item

import androidx.annotation.StringRes
import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.model.ItemIds.HEADER_ITEM_ID
import com.github.af2905.movieland.core.common.model.Model

data class HeaderItem(
    @StringRes val res: Int,
    override val id: Int = HEADER_ITEM_ID
) : Model(VIEW_TYPE) {

    companion object {
        val VIEW_TYPE = R.layout.list_item_header
    }
}