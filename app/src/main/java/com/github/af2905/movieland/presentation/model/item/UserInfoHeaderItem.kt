package com.github.af2905.movieland.presentation.model.item

import androidx.annotation.DrawableRes
import com.github.af2905.movieland.R
import com.github.af2905.movieland.presentation.model.ItemIds.USER_INFO_HEADER_ITEM_ID
import com.github.af2905.movieland.presentation.model.Model

private const val USER_NAME = "User Name"

data class UserInfoHeaderItem(
    override val id: Int = USER_INFO_HEADER_ITEM_ID,
    val name: String = USER_NAME,
    @DrawableRes val avatar: Int = R.drawable.ic_account
) : Model(VIEW_TYPE) {
    companion object {
        const val VIEW_TYPE = R.layout.list_item_user_info_header
    }
}
