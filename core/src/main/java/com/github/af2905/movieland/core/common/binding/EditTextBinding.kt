package com.github.af2905.movieland.core.common.binding

import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.github.af2905.movieland.core.common.model.item.SearchItem
import com.github.af2905.movieland.util.extension.afterTextChanged

@BindingAdapter("app:queryListener")
fun EditText.queryListener(listener: SearchItem.Listener?) {
    this.afterTextChanged { text -> listener?.textChanged(text.toString()) }
}

@BindingAdapter("app:clearQuery")
fun EditText.clearQuery(clear: Boolean) {
    if (clear) this.text.clear()
}