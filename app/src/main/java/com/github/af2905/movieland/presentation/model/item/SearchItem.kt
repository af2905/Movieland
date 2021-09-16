package com.github.af2905.movieland.presentation.model.item

import androidx.annotation.StringRes
import com.github.af2905.movieland.R
import com.github.af2905.movieland.presentation.common.ItemAdapter
import com.github.af2905.movieland.presentation.model.ItemIds.SEARCH_ITEM_ID
import com.github.af2905.movieland.presentation.model.Model

data class SearchItem(
    override val id: Int = SEARCH_ITEM_ID,
    var searchString: String = "",
    var queryHint: String = "",
    @StringRes var queryHintRes: Int = 0
) : Model(VIEW_TYPE) {

    companion object {
        const val VIEW_TYPE = R.layout.list_item_search
    }

    fun interface Listener : ItemAdapter.Listener {
        fun onItemClicked(item: MovieItem, position: Int)
    }
}