package com.github.af2905.movieland.helper.binding

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@BindingAdapter("app:refreshing")
fun SwipeRefreshLayout.refreshing(loading: Boolean) {
    isRefreshing = loading
}