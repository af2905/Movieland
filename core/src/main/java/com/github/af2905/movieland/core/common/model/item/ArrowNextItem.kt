package com.github.af2905.movieland.core.common.model.item

import androidx.annotation.StringRes
import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.ItemIds.NEXT_ITEM_ID
import com.github.af2905.movieland.core.common.model.Model

class ArrowNextItem(
    @StringRes val linkRes: Int = R.string.any_screen_show_all,
    val type: LinkItemType,
    override val id: Int = NEXT_ITEM_ID
) : Model(VIEW_TYPE) {

    companion object {
        val VIEW_TYPE = R.layout.list_item_arrow_next
    }

    fun interface Listener : ItemDelegate.Listener {
        fun onItemClicked(item: LinkItemType)
    }
}