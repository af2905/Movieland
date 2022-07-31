package com.github.af2905.movieland.core.common

import android.view.ViewGroup
import androidx.annotation.LayoutRes

open class ItemDelegate(
    @LayoutRes val viewType: Int, val listener: Listener? = null
) {

    open fun onCreateViewHolder(parent: ViewGroup): BindingViewHolder {
        return BindingViewHolder(parent.inflate(viewType))
    }

    interface Listener
}