package com.github.af2905.movieland.presentation.common.pager

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

fun setupPager(
    tabLayout: TabLayout,
    viewPager: ViewPager2,
    adapter: FragmentPagerAdapter
) {
    TabLayoutMediator(
        tabLayout, viewPager, false, true
    ) { tab, position ->
        tab.text = adapter.getPageItem(position).title.asCharSequence(viewPager.context)
    }.apply {
        viewPager.adapter = adapter
        attach()
    }
}