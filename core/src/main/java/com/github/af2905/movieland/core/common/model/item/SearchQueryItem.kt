package com.github.af2905.movieland.core.common.model.item

import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.Model

class SearchQueryItem(override val id: Int, val title: String) : Model(VIEW_TYPE) {

    companion object {
        val VIEW_TYPE = R.layout.list_item_search_query
    }

    fun interface Listener : ItemDelegate.Listener {
        fun onItemClicked(item: SearchQueryItem)
    }
}