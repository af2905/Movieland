package com.github.af2905.movieland.compose.components.navigation

import androidx.annotation.DrawableRes

data class BottomNavItem(
    val text: String,
    @DrawableRes val imageId: Int,
    val route: String,
    val hasBadge: Boolean = false
)