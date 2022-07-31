package com.github.af2905.movieland.core.common

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.github.af2905.movieland.core.common.model.Model

open class BindingViewHolder(open val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    var id: Int = 0; private set

    fun bind(item: Model, listener: ItemDelegate.Listener?) {
        this.id = item.id
        binding.setVariable(BR.item, item)
        binding.setVariable(BR.listener, listener)
        binding.setVariable(BR.position, bindingAdapterPosition)
        binding.executePendingBindings()
    }
}