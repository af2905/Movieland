package com.github.af2905.movieland.presentation.model.item

import com.github.af2905.movieland.R
import com.github.af2905.movieland.presentation.common.ItemDelegate
import com.github.af2905.movieland.presentation.model.Model

data class HomeMenuItem(
    val type: ProfileMenuItemType
) : Model(VIEW_TYPE) {

    override val id: Int
        get() = when (type) {
            ProfileMenuItemType.LIKED -> HOME_MENU_ITEM_LIKED_TYPE_ID
            ProfileMenuItemType.RECENTLY_VIEWED -> HOME_MENU_ITEM_RECENTLY_VIEWED_TYPE_ID
            ProfileMenuItemType.SETTINGS -> HOME_MENU_ITEM_SETTINGS_TYPE_ID
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
        const val VIEW_TYPE = R.layout.list_item_home_menu

        fun getList() = listOf<Model>(
            HomeMenuItem(ProfileMenuItemType.LIKED),
            HomeMenuItem(ProfileMenuItemType.RECENTLY_VIEWED),
            HomeMenuItem(ProfileMenuItemType.SETTINGS)
        )

        private const val HOME_MENU_ITEM_LIKED_TYPE_ID = -11
        private const val HOME_MENU_ITEM_RECENTLY_VIEWED_TYPE_ID = -12
        private const val HOME_MENU_ITEM_SETTINGS_TYPE_ID = -13
    }

    fun interface Listener : ItemDelegate.Listener {
        fun onItemClicked(item: HomeMenuItem)
    }
}

enum class HomeMenuItemType {
    LIKED, RECENTLY_VIEWED, SETTINGS
}