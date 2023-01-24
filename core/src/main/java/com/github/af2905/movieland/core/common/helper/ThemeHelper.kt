package com.github.af2905.movieland.core.common.helper

import android.content.Context
import android.content.res.Configuration

object ThemeHelper {

    fun isCurrentThemeDark(context: Context): Boolean {
        return context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }
}