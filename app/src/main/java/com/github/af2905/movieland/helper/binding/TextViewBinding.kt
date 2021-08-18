package com.github.af2905.movieland.helper.binding

import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter

@BindingAdapter("android:background")
fun TextView.setBackground(@DrawableRes res: Int?) = res?.let { setBackgroundResource(it) }

@BindingAdapter("android:text")
fun TextView.setText(@StringRes res: Int?) = res?.let { setText(res) }
