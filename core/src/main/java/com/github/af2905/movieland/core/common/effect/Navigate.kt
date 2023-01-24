package com.github.af2905.movieland.core.common.effect

import com.github.af2905.movieland.core.base.UiEffect
import com.github.af2905.movieland.core.base.navigator.Navigator

data class Navigate(val navigate: (Navigator) -> Unit) : UiEffect()