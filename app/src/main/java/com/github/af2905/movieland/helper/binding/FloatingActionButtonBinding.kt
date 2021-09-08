package com.github.af2905.movieland.helper.binding

import android.content.res.ColorStateList
import android.os.Build
import androidx.databinding.BindingAdapter
import com.github.af2905.movieland.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

@BindingAdapter("app:liked")
fun FloatingActionButton.setLikeImage(liked: Boolean) {
    val imageId = if (liked) R.drawable.ic_heart else R.drawable.ic_heart_outline
    val imageColor = if (liked) R.color.indianRed else R.color.white
    this.setImageResource(imageId)

    this.imageTintList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        ColorStateList.valueOf(
            resources.getColor(imageColor, context.theme)
        )
    } else {
        ColorStateList.valueOf(resources.getColor(imageColor))
    }
}