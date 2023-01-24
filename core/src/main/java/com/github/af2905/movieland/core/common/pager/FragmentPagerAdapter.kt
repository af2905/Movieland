package com.github.af2905.movieland.core.common.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentPagerAdapter (fragment: Fragment,
private val items: List<PageItem>
) : FragmentStateAdapter(fragment) {

    fun getPageItem(position: Int) = items[position]

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment = items[position].generator()
}