package com.github.af2905.movieland.presentation.common

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CustomLinearLayoutManager : LinearLayoutManager {

    constructor(
        context: Context,
        @RecyclerView.Orientation orientation: Int,
        reverseLayout: Boolean
    ) : super(context, orientation, reverseLayout)

    constructor(context: Context) : super(context)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    private var state: Parcelable? = null

    override fun onDetachedFromWindow(view: RecyclerView?, recycler: RecyclerView.Recycler?) {
        if (recycleChildrenOnDetach) {
            state = super.onSaveInstanceState()
        }
        super.onDetachedFromWindow(view, recycler)
    }

    override fun onSaveInstanceState(): Parcelable? {
        val localState = state
        state = null
        return localState ?: super.onSaveInstanceState()
    }
}