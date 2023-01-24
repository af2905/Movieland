package com.github.af2905.movieland.core.common.model.item

import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.Model

data class HomeMenuItem(
    val type: HomeMenuItemType
) : Model(VIEW_TYPE) {

    override val id: Int
        get() = when (type) {
            HomeMenuItemType.LIKED -> HOME_MENU_ITEM_LIKED_TYPE_ID
            HomeMenuItemType.RECENTLY_VIEWED -> HOME_MENU_ITEM_RECENTLY_VIEWED_TYPE_ID
            HomeMenuItemType.SETTINGS -> HOME_MENU_ITEM_SETTINGS_TYPE_ID
        }

    val nameRes: Int
        get() = when (type) {
            HomeMenuItemType.LIKED -> R.string.menu_item_liked_title
            HomeMenuItemType.RECENTLY_VIEWED -> R.string.menu_item_recently_viewed_title
            HomeMenuItemType.SETTINGS -> R.string.menu_item_settings_title
        }

    val drawableRes: Int
        get() = when (type) {
            HomeMenuItemType.LIKED -> R.drawable.ic_heart_outline
            HomeMenuItemType.RECENTLY_VIEWED -> R.drawable.ic_restart
            HomeMenuItemType.SETTINGS -> R.drawable.ic_cog_outline
        }

    companion object {
        val VIEW_TYPE = R.layout.list_item_home_menu

        fun getList() = listOf<Model>(
            HomeMenuItem(HomeMenuItemType.LIKED),
            HomeMenuItem(HomeMenuItemType.RECENTLY_VIEWED),
            HomeMenuItem(HomeMenuItemType.SETTINGS)
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