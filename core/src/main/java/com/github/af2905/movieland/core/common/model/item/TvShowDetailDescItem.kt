package com.github.af2905.movieland.core.common.model.item

import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.model.Model

data class TvShowDetailDescItem(val tvShowDetailItem: TvShowDetailItem) : Model(VIEW_TYPE) {

    override val id: Int
        get() = tvShowDetailItem.id

    companion object {
        val VIEW_TYPE = R.layout.list_item_tv_show_detail_desc
    }
}