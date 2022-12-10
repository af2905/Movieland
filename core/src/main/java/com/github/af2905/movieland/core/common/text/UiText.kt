package com.github.af2905.movieland.core.common.text

import android.content.Context

interface UiText {

    fun asCharSequence(context: Context): CharSequence

    companion object
}