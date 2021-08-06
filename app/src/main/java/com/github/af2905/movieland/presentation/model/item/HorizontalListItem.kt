package com.github.af2905.movieland.presentation.widget

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.af2905.movieland.R
import com.github.af2905.movieland.presentation.common.BindingViewHolder
import com.github.af2905.movieland.presentation.common.ItemAdapter
import com.github.af2905.movieland.presentation.common.ListAdapter
import com.github.af2905.movieland.presentation.model.Model

data class HorizontalListItem(val list: List<Model>) : Model(VIEW_TYPE) {

    companion object {
        const val VIEW_TYPE = R.layout.list_item_horizontal_list
    }
}

class HorizontalListAdapter(
    layout: Int,
    val adapter: () -> ListAdapter,
    val decoration: ((Context) -> RecyclerView.ItemDecoration)? = null
) : ItemAdapter(layout) {

    override fun onCreateViewHolder(parent: ViewGroup): BindingViewHolder {
        return super.onCreateViewHolder(parent).apply {
            binding.root.findViewById<RecyclerView>(R.id.recyclerView).apply {
                adapter = adapter()
                decoration?.let {
                    addItemDecoration(decoration.invoke(this.context))
                }
            }
        }
    }
}