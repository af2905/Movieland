package com.github.af2905.movieland.core.common.model.item

import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.BaseAdapter
import com.github.af2905.movieland.core.common.BindingViewHolder
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.ItemIds.PAGER_ITEM_ID
import com.github.af2905.movieland.core.common.model.Model
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

data class PagerItem(
    val list: List<Model>,
    override val id: Int = PAGER_ITEM_ID
) : Model(VIEW_TYPE) {

    override fun areItemsTheSame(item: Model): Boolean {
        return item is PagerItem && item.list == list
    }

    override fun areContentsTheSame(item: Model): Boolean {
        return super.areContentsTheSame(item)
    }

    companion object {
        val VIEW_TYPE = R.layout.list_item_pager
    }
}

class PagerAdapter(
    layout: Int,
    val adapter: () -> BaseAdapter,
    val callback: (Int) -> Unit = {}
) : ItemDelegate(layout) {

    override fun onCreateViewHolder(parent: ViewGroup): BindingViewHolder {
        return super.onCreateViewHolder(parent).apply {
            val viewPager = binding.root.findViewById<ViewPager2>(R.id.viewPager)
            val tabLayout = binding.root.findViewById<TabLayout>(R.id.tabLayout)

            viewPager.adapter = adapter()

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                //Some implementation
                callback(position)
            }.attach()
        }
    }
}