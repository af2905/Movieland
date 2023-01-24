package com.github.af2905.movieland.core.common

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

abstract class AppBarStateChangeListener  : AppBarLayout.OnOffsetChangedListener {

    enum class State {
        EXPANDED, COLLAPSED, IDLE
    }

    var currentState = State.IDLE
        private set

    private var prevOffset = 0

    override fun onOffsetChanged(appBarLayout: AppBarLayout, offset: Int) {
        val dy = offset - prevOffset
        prevOffset = offset

        currentState = when {
            offset == 0 -> {
                if (currentState != State.EXPANDED) {
                    onStateChanged(appBarLayout, State.EXPANDED)
                }
                State.EXPANDED
            }

            abs(offset) >= appBarLayout.totalScrollRange -> {
                if (currentState != State.COLLAPSED) {
                    onStateChanged(appBarLayout, State.COLLAPSED)
                }
                State.COLLAPSED
            }

            else -> {
                if (currentState != State.IDLE) {
                    onStateChanged(appBarLayout, State.IDLE)
                }
                State.IDLE
            }
        }

        onScrolled(currentState, dy)
    }

    abstract fun onStateChanged(appBarLayout: AppBarLayout, state: State)

    abstract fun onScrolled(state: State, dy: Int)

}