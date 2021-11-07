package com.github.af2905.movieland.helper.binding

import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.github.af2905.movieland.helper.extension.afterTextChanged
import com.github.af2905.movieland.presentation.model.item.SearchItem

@BindingAdapter("app:queryListener")
fun EditText.queryListener(listener: SearchItem.Listener) {
    this.afterTextChanged { text -> listener.textChanged(text.toString()) }
}

@BindingAdapter("app:clearQuery")
fun EditText.clearQuery(clear: Boolean) {
    if (clear) this.text.clear()
}