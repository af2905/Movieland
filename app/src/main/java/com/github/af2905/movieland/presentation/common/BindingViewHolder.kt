package com.github.af2905.movieland.presentation.common

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.github.af2905.movieland.presentation.model.Model

open class BindingViewHolder(open val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Model, listener: ItemAdapter.Listener?) {
        binding.setVariable(BR.item, item)
        binding.setVariable(BR.listener, listener)
        binding.setVariable(BR.position, bindingAdapterPosition)
        binding.executePendingBindings()
    }
}