package com.github.af2905.movieland.core.common.model.item

import androidx.annotation.StringRes
import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.ItemIds.HEADER_LINK_ITEM_ID
import com.github.af2905.movieland.core.common.model.Model

data class HeaderLinkItem(
    @StringRes val titleRes: Int,
    @StringRes val linkRes: Int,
    val type: HeaderLinkItemType,
    override val id: Int = HEADER_LINK_ITEM_ID
) : Model(VIEW_TYPE) {

    companion object {
        val VIEW_TYPE = R.layout.list_item_header_link
    }

    fun interface Listener : ItemDelegate.Listener {
        fun onItemClicked(item: HeaderLinkItem)
    }
}

enum class HeaderLinkItemType {
    MOVIES, TV_SHOWS, PEOPLE
}