package com.github.af2905.movieland.presentation.model.item

import androidx.annotation.StringRes
import com.github.af2905.movieland.R
import com.github.af2905.movieland.presentation.model.ItemIds.HEADER_ITEM_ID
import com.github.af2905.movieland.presentation.model.Model

data class HeaderItem(@StringRes val res: Int, override val id: Int = HEADER_ITEM_ID) :
    Model(VIEW_TYPE) {

    companion object {
        const val VIEW_TYPE = R.layout.list_item_header
    }
}