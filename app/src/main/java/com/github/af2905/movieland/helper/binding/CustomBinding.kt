package com.github.af2905.movieland.helper.binding

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.github.af2905.movieland.R
import com.github.af2905.movieland.helper.extension.afterTextChanged
import com.github.af2905.movieland.presentation.model.item.SearchItem
import com.github.af2905.movieland.presentation.widget.SearchViewBar

@BindingAdapter("app:queryListener")
fun SearchViewBar.queryListener(listener: SearchItem.Listener) {
    val search = this.findViewById<EditText>(R.id.searchInput)
    val delete = this.findViewById<ImageView>(R.id.deleteIcon)

    search.afterTextChanged { text ->
        if (!text.isNullOrEmpty() && !delete.isVisible) {
            delete.visibility = View.VISIBLE
        }
        if (text.isNullOrEmpty() && delete.isVisible) {
            delete.visibility = View.GONE
        }
    }
}
