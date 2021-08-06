package com.github.af2905.movieland.presentation.model

import androidx.annotation.LayoutRes

abstract class Model(
    @LayoutRes val viewType: Int
) : SameItem {
    override fun areItemsTheSame(item: Model): Boolean {
        return item === this
    }

    override fun areContentsTheSame(item: Model): Boolean {
        return item == this
    }
}