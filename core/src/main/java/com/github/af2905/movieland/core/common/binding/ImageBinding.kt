package com.github.af2905.movieland.core.common.binding

import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.github.af2905.movieland.core.R
import com.google.android.material.imageview.ShapeableImageView

@BindingAdapter("app:src")
fun ShapeableImageView.loadImage(src: String?) {
    val options: RequestOptions = RequestOptions()
        .error(R.drawable.ic_placeholder)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)
    Glide.with(this)
        .load(src)
        .apply(options)
        .into(this)
}

@BindingAdapter("app:src")
fun ShapeableImageView.loadImage(@DrawableRes imageRes: Int? = null) {
    imageRes?.let { setImageResource(imageRes) }
}

@BindingAdapter("app:src")
fun AppCompatImageView.setImage(@DrawableRes image: Int) = setImageResource(image)
