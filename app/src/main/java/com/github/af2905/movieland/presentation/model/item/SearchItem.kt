package com.github.af2905.movieland.presentation.model.item

import androidx.annotation.StringRes
import com.github.af2905.movieland.R
import com.github.af2905.movieland.helper.extension.empty
import com.github.af2905.movieland.presentation.common.ItemDelegate
import com.github.af2905.movieland.presentation.model.ItemIds.SEARCH_ITEM_ID
import com.github.af2905.movieland.presentation.model.Model

data class SearchItem(
    override val id: Int = SEARCH_ITEM_ID,
    val searchString: String = String.empty,
    val queryHint: String = "",
    @StringRes val queryHintRes: Int = R.string.hint_search_query,
    val deleteVisible: Boolean = false,
    val clearText: Boolean = false
) : Model(VIEW_TYPE) {

    companion object {
        const val VIEW_TYPE = R.layout.list_item_search
        const val TEXT_ENTERED_DEBOUNCE_MILLIS = 500L
    }

    interface Listener : ItemDelegate.Listener {
        fun textChanged(text: String)
        fun deleteTextClicked()
    }
}