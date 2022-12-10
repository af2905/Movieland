package com.github.af2905.movieland.core.common.model.item

import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.Model

data class ProfileMenuItem(
    val type: ProfileMenuItemType
) : Model(VIEW_TYPE) {

    override val id: Int
        get() = when (type) {
            ProfileMenuItemType.LIKED -> PROFILE_MENU_ITEM_LIKED_TYPE_ID
            ProfileMenuItemType.RECENTLY_VIEWED -> PROFILE_MENU_ITEM_RECENTLY_VIEWED_TYPE_ID
            ProfileMenuItemType.SETTINGS -> PROFILE_MENU_ITEM_SETTINGS_TYPE_ID
        }

    val nameRes: Int
        get() = when (type) {
            ProfileMenuItemType.LIKED -> R.string.menu_item_liked_title
            ProfileMenuItemType.RECENTLY_VIEWED -> R.string.menu_item_recently_viewed_title
            ProfileMenuItemType.SETTINGS -> R.string.menu_item_settings_title
        }

    val drawableRes: Int
        get() = when (type) {
            ProfileMenuItemType.LIKED -> R.drawable.ic_heart_outline
            ProfileMenuItemType.RECENTLY_VIEWED -> R.drawable.ic_restart
            ProfileMenuItemType.SETTINGS -> R.drawable.ic_cog_outline
        }

    companion object {
        val VIEW_TYPE = R.layout.list_item_profile_menu

        fun getList() = listOf<Model>(
            ProfileMenuItem(ProfileMenuItemType.LIKED),
            ProfileMenuItem(ProfileMenuItemType.RECENTLY_VIEWED),
            ProfileMenuItem(ProfileMenuItemType.SETTINGS)
        )

        private const val PROFILE_MENU_ITEM_LIKED_TYPE_ID = -21
        private const val PROFILE_MENU_ITEM_RECENTLY_VIEWED_TYPE_ID = -22
        private const val PROFILE_MENU_ITEM_SETTINGS_TYPE_ID = -23
    }

    fun interface Listener : ItemDelegate.Listener {
        fun onItemClicked(item: ProfileMenuItem)
    }
}

enum class ProfileMenuItemType {
    LIKED, RECENTLY_VIEWED, SETTINGS
}