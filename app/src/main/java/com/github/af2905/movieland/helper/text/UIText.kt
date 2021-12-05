package com.github.af2905.movieland.helper.text

import android.content.Context

interface UIText {

    fun asCharSequence(context: Context): CharSequence

    companion object
}