package com.github.af2905.movieland.home.presentation.item

import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.Model
import com.github.af2905.movieland.core.common.model.item.PersonItem
import com.github.af2905.movieland.home.R

data class PopularPersonItem(
    val personItem: PersonItem,
    override val id: Int = personItem.id
) : Model(VIEW_TYPE) {

    companion object {
        val VIEW_TYPE = R.layout.list_item_popular_person
    }

    fun interface Listener : ItemDelegate.Listener {
        fun onItemClicked(item: PersonItem)
    }
}