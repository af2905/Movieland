package com.github.af2905.movieland.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.view.isVisible
import com.github.af2905.movieland.R
import com.github.af2905.movieland.helper.extension.afterTextChanged

class SearchBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private val editText: EditText by lazy { findViewById(R.id.searchEditText) }
    private var searchEditText: EditText? = null
    private var deleteTextButton: ImageView? = null

    private var hint: String = ""
    private var isCancelVisible: Boolean = true

    init {
        LayoutInflater.from(context).inflate(R.layout.search_toolbar, this)
        searchEditText = findViewById(R.id.searchEditText)
        deleteTextButton = findViewById(R.id.deleteTextButton)

        if (attrs != null) {
            context.obtainStyledAttributes(attrs, R.styleable.SearchBar).apply {
                hint = getString(R.styleable.SearchBar_hint).orEmpty()
                isCancelVisible = getBoolean(R.styleable.SearchBar_cancel_visible, true)
                recycle()
            }
        }
        searchEditText?.hint = hint
        deleteTextButton?.setOnClickListener { searchEditText?.text?.clear() }
    }

    fun setText(text: String?) {
        this.editText.setText(text)
    }

    fun clear() {
        this.editText.setText("")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        searchEditText?.afterTextChanged { text ->
            if (text?.isNotBlank() == true && !deleteTextButton?.isVisible!!) {
                deleteTextButton?.visibility = View.VISIBLE
            }
            if (text.isNullOrEmpty() && deleteTextButton?.isVisible == true) {
                deleteTextButton?.visibility = View.GONE
            }
        }
    }
}