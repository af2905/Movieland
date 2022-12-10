package com.github.af2905.movieland.core.common.binding

import android.content.res.ColorStateList
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.model.item.MovieItem
import com.github.af2905.movieland.util.extension.formatVoteAverage

private const val DEFAULT_RATING_FORMAT_DIGITS = 1

@BindingAdapter("android:background")
fun TextView.setBackground(@DrawableRes res: Int?) = res?.let { setBackgroundResource(it) }

@BindingAdapter("android:text")
fun TextView.setText(@StringRes res: Int?) = res?.let { setText(res) }

@BindingAdapter("app:voteAverage")
fun TextView.setVoteAverage(voteAverage: Double?) {
    this.text = voteAverage?.formatVoteAverage(DEFAULT_RATING_FORMAT_DIGITS).orEmpty()
}

@BindingAdapter("app:voteAverageTextColor")
fun TextView.setVoteAverageTextColor(voteAverage: Double?) {
    val voteAverageTextColor = voteAverage?.let {
        when (voteAverage) {
            in MovieItem.RAD_RANGE -> R.color.indianRed
            in MovieItem.GREEN_RANGE -> R.color.forestGreen
            else -> R.color.darkGrey
        }
    } ?: R.color.darkGrey
    val color = ColorStateList.valueOf(resources.getColor(voteAverageTextColor, context.theme))
    this.setTextColor(color)
}

@BindingAdapter("app:text")
fun TextView.setResText(@StringRes textRes: Int) = setText(textRes)