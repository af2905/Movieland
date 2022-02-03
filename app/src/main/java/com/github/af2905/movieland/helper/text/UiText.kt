package com.github.af2905.movieland.helper.text

import android.content.Context

interface UiText {

    fun asCharSequence(context: Context): CharSequence

    companion object
}