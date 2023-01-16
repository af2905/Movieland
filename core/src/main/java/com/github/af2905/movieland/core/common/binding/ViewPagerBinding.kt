package com.github.af2905.movieland.core.common.binding

import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.github.af2905.movieland.core.common.BaseAdapter
import com.github.af2905.movieland.core.common.model.Model

@BindingAdapter("viewPagerSetData")
fun ViewPager2.setData(items: List<Model>?) {
    isVisible = items != null
    (adapter as? BaseAdapter)?.let { items?.let { items -> it.submitList(items) } }
}