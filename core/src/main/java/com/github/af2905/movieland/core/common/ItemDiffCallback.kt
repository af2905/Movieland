package com.github.af2905.movieland.core.common

import androidx.recyclerview.widget.DiffUtil
import com.github.af2905.movieland.core.common.model.Model

class ItemDiffCallback : DiffUtil.ItemCallback<Model>() {
    override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean {
        return oldItem.areItemsTheSame(newItem)
    }

    override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean {
        return oldItem.areContentsTheSame(newItem)
    }
}