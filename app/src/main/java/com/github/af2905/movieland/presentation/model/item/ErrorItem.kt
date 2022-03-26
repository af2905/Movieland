package com.github.af2905.movieland.presentation.model.item

import androidx.annotation.StringRes
import com.github.af2905.movieland.R
import com.github.af2905.movieland.presentation.common.ItemDelegate
import com.github.af2905.movieland.presentation.model.ItemIds.ERROR_ITEM_ID
import com.github.af2905.movieland.presentation.model.Model

data class ErrorItem(
@StringRes val titleRes: Int = R.string.message_no_internet_connection,
@StringRes val buttonRes: Int = R.string.any_screen_try_again,
override val id: Int = ERROR_ITEM_ID
) :
Model(VIEW_TYPE) {

    companion object {
        const val VIEW_TYPE = R.layout.list_item_error
    }

    fun interface Listener : ItemDelegate.Listener {
        fun onItemClicked()
    }
}