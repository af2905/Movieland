package com.github.af2905.movieland.presentation.model.item

import com.github.af2905.movieland.R
import com.github.af2905.movieland.presentation.common.ItemDelegate
import com.github.af2905.movieland.presentation.model.Model

class SearchQueryItem(override val id: Int, val title: String) : Model(VIEW_TYPE) {

    companion object {
        const val VIEW_TYPE = R.layout.list_item_search_query
    }

    fun interface Listener : ItemDelegate.Listener {
        fun onItemClicked(item: SearchQueryItem)
    }
}