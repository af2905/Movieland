package com.github.af2905.movieland.core.common.model.item

import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.model.ItemIds.LOADING_ITEM_ID
import com.github.af2905.movieland.core.common.model.Model

class LoadingItem(override val id: Int = LOADING_ITEM_ID) : Model(VIEW_TYPE){

    companion object{
        val VIEW_TYPE = R.layout.list_item_loading
    }
}