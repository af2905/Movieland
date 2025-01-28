package com.github.af2905.movieland.compose.components.divider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.af2905.movieland.compose.theme.AppTheme

@Composable
fun AppHorizontalDivider() {
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .height(0.5.dp)
            .background(AppTheme.colors.background.divider)
    )
}