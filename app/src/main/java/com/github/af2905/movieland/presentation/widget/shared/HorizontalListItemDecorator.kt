package com.github.af2905.movieland.presentation.widget.shared

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.af2905.movieland.presentation.common.ListAdapter

class HorizontalListItemDecorator(
    val marginTop: Int = 0,
    val marginBottom: Int = 0,
    val marginStart: Int = 0,
    val marginEnd: Int = 0,
    val spacing: Int = 0,
    val startPosition: Int = 0,
    val lastPosition: Int = Int.MAX_VALUE
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            val pos = parent.getChildAdapterPosition(view)
            val lastPos = kotlin.math.min(
                lastPosition, (parent.adapter as ListAdapter).itemCount - 1
            )
            if (pos == startPosition) left = marginStart
            right = if (pos == lastPos) marginEnd else spacing
            top = marginTop
            bottom = marginBottom
        }
    }
}