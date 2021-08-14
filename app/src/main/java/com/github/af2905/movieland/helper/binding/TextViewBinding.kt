package com.github.af2905.movieland.helper.binding

import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter("app:background")
fun TextView.setBackground(@DrawableRes res: Int?) {
    res?.let { setBackgroundResource(it) }
}