package com.github.af2905.movieland.helper.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.github.af2905.movieland.R

@BindingAdapter("app:src")
fun ImageView.loadImage(src: String?) {
    val options: RequestOptions = RequestOptions()
        .placeholder(R.drawable.ic_placeholder)
        .error(R.drawable.ic_placeholder)
        .transform(
            FitCenter(),
            RoundedCorners(context.resources.getDimension(R.dimen.default_corners_radius).toInt())
        )
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)
    Glide.with(this)
        .load(src)
        .apply(options)
        .into(this)
}