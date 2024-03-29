package com.github.af2905.movieland.core.common.pager

import androidx.fragment.app.Fragment
import com.github.af2905.movieland.core.common.text.UiText

data class PageItem(
    val title: UiText,
    val generator: () -> Fragment
)