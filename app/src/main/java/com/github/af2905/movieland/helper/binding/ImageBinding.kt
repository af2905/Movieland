package com.github.af2905.movieland.helper.binding

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.github.af2905.movieland.R
import com.google.android.material.imageview.ShapeableImageView

@BindingAdapter("app:src")
fun ShapeableImageView.loadImage(src: String?) {
    val options: RequestOptions = RequestOptions()
        .placeholder(R.drawable.ic_placeholder)
        .error(R.drawable.ic_placeholder)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)
    Glide.with(this)
        .load(src)
        .apply(options)
        .into(this)
}