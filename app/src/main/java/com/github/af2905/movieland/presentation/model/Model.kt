package com.github.af2905.movieland.presentation.model

import androidx.annotation.LayoutRes

abstract class Model(
    @LayoutRes val viewType: Int
) : SameItem {

    abstract val id: Int

    override fun areItemsTheSame(item: Model): Boolean = when{
        this::class != item::class -> false
        else -> this.id == item.id
    }

    override fun areContentsTheSame(item: Model): Boolean {
        return item == this
    }
}