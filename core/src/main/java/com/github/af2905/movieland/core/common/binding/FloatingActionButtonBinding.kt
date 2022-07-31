package com.github.af2905.movieland.core.common.binding

import android.content.res.ColorStateList
import androidx.databinding.BindingAdapter
import com.github.af2905.movieland.core.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

@BindingAdapter("app:liked")
fun FloatingActionButton.setLikeImage(liked: Boolean) {
    val imageId = if (liked) R.drawable.ic_heart else R.drawable.ic_heart_outline
    val imageColor = if (liked) R.color.indianRed else R.color.white
    this.setImageResource(imageId)

    this.imageTintList =
        ColorStateList.valueOf(
            resources.getColor(imageColor, context.theme)
        )
}