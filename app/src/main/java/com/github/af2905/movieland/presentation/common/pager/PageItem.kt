package com.github.af2905.movieland.presentation.common.pager

import androidx.fragment.app.Fragment
import com.github.af2905.movieland.helper.text.UiText

data class PageItem(
    val title: UiText,
    val generator: () -> Fragment
)