package com.github.af2905.movieland.helper.binding

import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.github.af2905.movieland.helper.extension.formatVoteAverage

private const val DEFAULT_RATING_FORMAT_DIGITS = 1

@BindingAdapter("android:background")
fun TextView.setBackground(@DrawableRes res: Int?) = res?.let { setBackgroundResource(it) }

@BindingAdapter("android:text")
fun TextView.setText(@StringRes res: Int?) = res?.let { setText(res) }

@BindingAdapter("app:voteAverage")
fun TextView.setVoteAverage(voteAverage: Double?) {
    this.text = voteAverage?.formatVoteAverage(DEFAULT_RATING_FORMAT_DIGITS).orEmpty()
}
