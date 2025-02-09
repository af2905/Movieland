package com.github.af2905.movieland.core.common.helper

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StringProvider @Inject constructor(@ApplicationContext private val context: Context) {
    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return context.getString(resId, *formatArgs)
    }
}