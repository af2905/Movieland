package com.github.af2905.movieland.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.github.af2905.movieland.presentation.model.Model

class BaseAdapter(vararg adapters: ItemAdapter) : ListAdapter<Model, BindingViewHolder>(ItemDiffCallback()) {

    private val delegateAdapters = adapters.associateBy { it.viewType }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        currentList[position].let { item -> holder.bind(item, delegateAdapters[item.viewType]?.listener) }
    }

    override fun getItemCount(): Int = currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        return delegateAdapters[viewType]?.onCreateViewHolder(parent)
            ?: BindingViewHolder(parent.inflate(viewType))
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType
    }
}

fun ViewGroup.inflate(@LayoutRes layout: Int): ViewDataBinding = DataBindingUtil.inflate(
    LayoutInflater.from(this.context), layout, this, false
)