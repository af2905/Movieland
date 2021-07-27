package com.github.af2905.movieland.helper.binding

import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.af2905.movieland.presentation.common.ListAdapter
import com.github.af2905.movieland.presentation.model.Model

@BindingAdapter("recyclerSetData")
fun RecyclerView.setData(items: List<Model>?) {
    isVisible = items != null
    @Suppress("UNCHECKED_CAST")
    (adapter as? ListAdapter)?.let { items?.let { items -> it.items = items } }
}

@BindingAdapter(value = ["linearLayoutManager", "app:reverseLayout"], requireAll = false)
fun RecyclerView.setLinearLayoutManager(horizontal: Boolean, reverseLayout: Boolean = false) {
    val orientation = if (horizontal) {
        LinearLayoutManager.HORIZONTAL
    } else {
        LinearLayoutManager.VERTICAL
    }
    layoutManager = LinearLayoutManager(context, orientation, reverseLayout)
}

@BindingAdapter("gridLayoutManager")
fun RecyclerView.setGridLayoutManager(spanCount: Int) {
    layoutManager = GridLayoutManager(context, spanCount)
}