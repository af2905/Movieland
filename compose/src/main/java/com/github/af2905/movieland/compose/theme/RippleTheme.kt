package com.github.af2905.movieland.compose.theme

import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color


@Composable
fun RippleTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val rippleTheme = object : RippleTheme {
        override fun defaultColor(): Color = AppTheme.colors.background.actionRipple

        override fun rippleAlpha(): RippleAlpha = RippleTheme.defaultRippleAlpha(
            Color.Black,
            lightTheme = !darkTheme
        )
    }

    CompositionLocalProvider(
        LocalRippleTheme provides rippleTheme,
        content = content
    )
}