package com.github.af2905.movieland.compose.components.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val text: String,
    val icon : ImageVector,
    val selectedIcon: ImageVector,
    val route: String,
    val hasBadge: Boolean = false
)