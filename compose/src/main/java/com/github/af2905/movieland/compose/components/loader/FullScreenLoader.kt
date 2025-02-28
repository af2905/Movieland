package com.github.af2905.movieland.compose.components.loader

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.af2905.movieland.compose.theme.AppTheme

@Composable
fun FullScreenLoader(visible: Boolean = true) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.background.default.copy(alpha = 0.4f))

        ) {
            CircularProgressIndicator(
                color = AppTheme.colors.theme.tint,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
@Preview(showSystemUi = false,showBackground = false)
private fun ComponentPreview() {
    FullScreenLoader()
}