package com.github.af2905.movieland.core.common.model.item

import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.Model

data class PersonItemV2(
    val personItem: PersonItem,
    override val id: Int = personItem.id
) : Model(VIEW_TYPE) {

    companion object {
        val VIEW_TYPE = R.layout.list_item_person_v2
    }

    fun interface Listener : ItemDelegate.Listener {
        fun onItemClicked(item: PersonItem)
    }
}