package com.github.af2905.movieland.presentation.common

import android.os.Parcelable
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.af2905.movieland.presentation.model.Model
import java.lang.ref.WeakReference

class NestedRecyclerViewStateAdapter() : ListAdapter<Model, BindingViewHolder>(ItemDiffCallback()) {

    private val layoutManagerStates = hashMapOf<Long, Parcelable?>()
    private val visibleScrollableViews = hashMapOf<Int, ViewHolderRef>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        (holder.binding.root as RecyclerView).layoutManager?.let {
            val state: Parcelable? = layoutManagerStates[holder.itemId]
            if (state != null) {
                it.onRestoreInstanceState(state)
            } else {
                it.scrollToPosition(0)
            }
        }
        visibleScrollableViews[holder.hashCode()] =
            ViewHolderRef(holder.itemId, WeakReference(holder))
    }

    @CallSuper
    override fun onViewRecycled(holder: BindingViewHolder) {
        val state = (holder.binding.root as RecyclerView).layoutManager?.onSaveInstanceState()
        layoutManagerStates[holder.itemId] = state
        visibleScrollableViews.remove(holder.hashCode())
        super.onViewRecycled(holder)
    }

    @CallSuper
    override fun submitList(list: List<Model>?) {
        saveState()
        super.submitList(list)
    }

    private fun saveState() {
        visibleScrollableViews.values.forEach { item ->
            item.reference.get()?.let {
                layoutManagerStates[item.id] =
                    (it.binding.root as RecyclerView).layoutManager?.onSaveInstanceState()
            }
        }
        visibleScrollableViews.clear()
    }

    fun clearState() {
        layoutManagerStates.clear()
        visibleScrollableViews.clear()
    }

    private data class ViewHolderRef(
        val id: Long,
        val reference: WeakReference<BindingViewHolder>
    )
}