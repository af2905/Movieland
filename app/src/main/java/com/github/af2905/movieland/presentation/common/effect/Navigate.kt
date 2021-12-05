package com.github.af2905.movieland.presentation.common.effect

import com.github.af2905.movieland.helper.navigator.Navigator
import com.github.af2905.movieland.presentation.base.UIEffect

data class Navigate(val navigate: (Navigator) -> Unit) : UIEffect()