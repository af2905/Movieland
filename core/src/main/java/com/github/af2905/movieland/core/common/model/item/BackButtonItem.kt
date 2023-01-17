package com.github.af2905.movieland.core.common.model.item

import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.ItemIds.BACK_BUTTON_ITEM_ID
import com.github.af2905.movieland.core.common.model.Model

data class BackButtonItem(override val id: Int = BACK_BUTTON_ITEM_ID) : Model(VIEW_TYPE) {
    companion object {
        val VIEW_TYPE = R.layout.list_item_back_button
    }

    fun interface Listener : ItemDelegate.Listener {
        fun onItemClicked()
    }
}