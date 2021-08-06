package com.github.af2905.movieland.presentation.model

interface SameItem {
    fun areItemsTheSame(item: Model): Boolean
    fun areContentsTheSame(item: Model): Boolean
}