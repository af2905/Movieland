package com.github.af2905.movieland.core.common.model.item

import androidx.annotation.StringRes
import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.ItemIds.ERROR_ITEM_ID
import com.github.af2905.movieland.core.common.model.Model

data class ErrorItem(
    val errorMessage: String = "",
    val errorButtonVisible: Boolean = true,
    @StringRes val titleRes: Int = R.string.message_no_internet_connection,
    @StringRes val buttonRes: Int = R.string.any_screen_try_again,
    override val id: Int = ERROR_ITEM_ID
) :
    Model(VIEW_TYPE) {

    companion object {
        val VIEW_TYPE = R.layout.list_item_error
    }

    fun interface Listener : ItemDelegate.Listener {
        fun onItemClicked()
    }
}