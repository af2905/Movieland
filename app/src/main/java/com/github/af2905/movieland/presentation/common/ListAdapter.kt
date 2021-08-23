package com.github.af2905.movieland.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.af2905.movieland.presentation.model.Model

class ListAdapter(vararg adapters: ItemAdapter) : RecyclerView.Adapter<BindingViewHolder>() {

    var items: List<Model>
        get() = differ.currentList
        set(value) {
            differ.submitList(value.toList())
        }

    private val delegateAdapters = adapters.associateBy { it.viewType }

    private val diffCallback: DiffUtil.ItemCallback<Model> =
        object : DiffUtil.ItemCallback<Model>() {

            override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean {
                return oldItem.areItemsTheSame(newItem)
            }

            override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean {
                return oldItem.areContentsTheSame(newItem)
            }
        }

    private val differ: AsyncListDiffer<Model> = AsyncListDiffer(this, diffCallback)

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        items[position].let { item -> holder.bind(item, delegateAdapters[item.viewType]?.listener) }
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        return delegateAdapters[viewType]?.onCreateViewHolder(parent)
            ?: BindingViewHolder(parent.inflate(viewType))
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }
}

fun ViewGroup.inflate(@LayoutRes layout: Int): ViewDataBinding = DataBindingUtil.inflate(
    LayoutInflater.from(this.context), layout, this, false
)

