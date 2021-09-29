package com.github.af2905.movieland.presentation.model.item

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.af2905.movieland.R
import com.github.af2905.movieland.presentation.common.BaseAdapter
import com.github.af2905.movieland.presentation.common.BindingViewHolder
import com.github.af2905.movieland.presentation.common.ItemDelegate
import com.github.af2905.movieland.presentation.model.ItemIds.HORIZONTAL_ITEM_LIST_ID
import com.github.af2905.movieland.presentation.model.Model

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
        const val VIEW_TYPE = R.layout.list_item_horizontal_list
    }
}

class HorizontalListAdapter(
    layout: Int,
    val adapter: () -> BaseAdapter,
    val decoration: ((Context) -> RecyclerView.ItemDecoration)? = null
) : ItemDelegate(layout) {

    override fun onCreateViewHolder(parent: ViewGroup): BindingViewHolder {
        return super.onCreateViewHolder(parent).apply {
            binding.root.findViewById<RecyclerView>(R.id.recyclerView).apply {
                layoutManager =
                    LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = adapter()
                decoration?.let {
                    addItemDecoration(decoration.invoke(this.context))
                }
            }
        }
    }
}