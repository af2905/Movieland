package com.github.af2905.movieland.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import com.github.af2905.movieland.R
import com.github.af2905.movieland.compose.components.navigation.BottomNavItem
import com.github.af2905.movieland.core.compose.AppNavRoutes

val bottomNavItems = listOf(
    BottomNavItem(
        textRes = R.string.nav_home,
        icon = Icons.Outlined.Home,
        selectedIcon = Icons.Default.Home,
        route = AppNavRoutes.Home.route
    ),
    BottomNavItem(
        textRes = R.string.nav_search,
        icon = Icons.Outlined.Search,
        selectedIcon = Icons.Default.Search,
        route = AppNavRoutes.Search.route
    ),
    BottomNavItem(
        textRes = R.string.nav_library,
        icon = Icons.Outlined.Bookmarks,
        selectedIcon = Icons.Default.Bookmarks,
        route = AppNavRoutes.Library.route
    ),
    BottomNavItem(
        textRes = R.string.nav_profile,
        icon = Icons.Outlined.Person,
        selectedIcon = Icons.Default.Person,
        route = AppNavRoutes.Profile.route
    )
)
