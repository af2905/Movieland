package com.github.af2905.movieland.core.common.model.item

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.BaseAdapter
import com.github.af2905.movieland.core.common.BindingViewHolder
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.ItemIds.HORIZONTAL_ITEM_LIST_ID
import com.github.af2905.movieland.core.common.model.Model

data class HorizontalListItem(
    val list: List<Model>,
    override val id: Int = HORIZONTAL_ITEM_LIST_ID
) : Model(VIEW_TYPE) {

    override fun areItemsTheSame(item: Model): Boolean {
        return item is HorizontalListItem && item.list == list
    }

    override fun areContentsTheSame(item: Model): Boolean {
        return super.areContentsTheSame(item)
    }

    companion object {
        val VIEW_TYPE = R.layout.list_item_horizontal_list
    }
}

class HorizontalListAdapter(
    layout: Int,
    val adapter: () -> BaseAdapter,
    val snapHelper: SnapHelper? = null,
    val decoration: ((Context) -> RecyclerView.ItemDecoration)? = null,
) : ItemDelegate(layout) {

    override fun onCreateViewHolder(parent: ViewGroup): BindingViewHolder {
        return super.onCreateViewHolder(parent).apply {
            val recyclerView = binding.root.findViewById<RecyclerView>(R.id.recyclerView).apply {
                layoutManager =
                    LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = adapter()
                decoration?.let {
                    addItemDecoration(decoration.invoke(this.context))
                }
            }
            snapHelper?.attachToRecyclerView(recyclerView)
        }
    }
}