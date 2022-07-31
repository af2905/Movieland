package com.github.af2905.movieland.core.common

import android.os.Parcelable
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.model.Model
import java.lang.ref.WeakReference

class NestedRecyclerViewStateAdapter(vararg delegates: ItemDelegate) : BaseAdapter(*delegates) {

    private val layoutManagerStates = hashMapOf<Int, Parcelable?>()
    private val visibleScrollableViews = hashMapOf<Int, ViewHolderRef>()

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
    }

    @CallSuper
    override fun onViewRecycled(holder: BindingViewHolder) {
        super.onViewRecycled(holder)
        val state =
            (holder.binding.root).findViewById<RecyclerView>(R.id.recyclerView)?.layoutManager?.onSaveInstanceState()
        layoutManagerStates[holder.id] = state
        visibleScrollableViews.remove(holder.hashCode())
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