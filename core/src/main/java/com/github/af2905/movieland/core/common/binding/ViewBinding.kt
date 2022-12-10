package com.github.af2905.movieland.core.common.binding

import android.view.View
import androidx.annotation.DimenRes
import androidx.databinding.BindingAdapter

@BindingAdapter("app:visible")
fun View.visibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("layout_height")
fun View.setLayoutHeight(@DimenRes res: Int) {
    layoutParams = layoutParams.also { it.height = resources.getDimension(res).toInt() }
}