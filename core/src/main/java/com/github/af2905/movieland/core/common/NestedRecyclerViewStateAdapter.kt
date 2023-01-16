package com.github.af2905.movieland.core.common

import android.os.Parcelable
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.model.ItemIds
import com.github.af2905.movieland.core.common.model.Model
import java.lang.ref.WeakReference

class NestedRecyclerViewStateAdapter(vararg delegates: ItemDelegate) : BaseAdapter(*delegates) {

    private val layoutManagerStates = hashMapOf<Int, Parcelable?>()
    private val visibleScrollableViews = hashMapOf<Int, ViewHolderRef>()
    private var viewPagerPosition: Int? = null

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        (holder.binding.root).findViewById<RecyclerView>(R.id.recyclerView)?.layoutManager?.let {
            val state: Parcelable? = layoutManagerStates[holder.id]
            if (state != null) {
                it.onRestoreInstanceState(state)
            } else {
                it.scrollToPosition(0)
            }
        }
        visibleScrollableViews[holder.hashCode()] =
            ViewHolderRef(holder.id, WeakReference(holder))

        (holder.binding.root).findViewById<ViewPager2>(R.id.viewPager)?.let {
            if (viewPagerPosition != null) {
                it.setCurrentItem(viewPagerPosition!!, false)
            }
            viewPagerPosition = null
        }
    }

    @CallSuper
    override fun onViewRecycled(holder: BindingViewHolder) {
        super.onViewRecycled(holder)
        val state =
            (holder.binding.root).findViewById<RecyclerView>(R.id.recyclerView)?.layoutManager?.onSaveInstanceState()
        layoutManagerStates[holder.id] = state
        visibleScrollableViews.remove(holder.hashCode())

        if (holder.id == ItemIds.PAGER_ITEM_ID) {
            viewPagerPosition =
                (holder.binding.root).findViewById<ViewPager2>(R.id.viewPager)?.currentItem
        }
    }

    @CallSuper
    override fun submitList(list: List<Model>?) {
        saveState()
        super.submitList(list)
    }

    private fun saveState() {
        visibleScrollableViews.values.forEach { item ->
            item.reference.get()?.let { holder ->
                layoutManagerStates[item.id] =
                    (holder.binding.root).findViewById<RecyclerView>(R.id.recyclerView)?.layoutManager?.onSaveInstanceState()

                if (holder.id == ItemIds.PAGER_ITEM_ID) {
                    viewPagerPosition =
                        (holder.binding.root).findViewById<ViewPager2>(R.id.viewPager)?.currentItem
                }
            }
        }
        visibleScrollableViews.clear()
    }

    fun clearState() {
        layoutManagerStates.clear()
        visibleScrollableViews.clear()
    }

    private data class ViewHolderRef(
        val id: Int,
        val reference: WeakReference<BindingViewHolder>
    )
}