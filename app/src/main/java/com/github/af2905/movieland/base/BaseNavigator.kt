package com.github.af2905.movieland.base

import androidx.fragment.app.Fragment

abstract class BaseNavigator {

    protected abstract fun forwardTo(fragment: Fragment, tag: String)
    protected abstract fun backTo(tag: String)
    protected abstract fun back()
}