package com.github.af2905.movieland.compose.modifier

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

@Composable
fun Modifier.clickableUnbounded(onClick: () -> Unit): Modifier = composed {
    this.clickable(
        indication = ripple(bounded = false),
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}