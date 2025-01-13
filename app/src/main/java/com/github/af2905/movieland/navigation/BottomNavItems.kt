package com.github.af2905.movieland.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import com.github.af2905.movieland.compose.components.navigation.BottomNavItem
import com.github.af2905.movieland.core.compose.AppNavRoutes

//TODO localize strings
val bottomNavItems = listOf(
    BottomNavItem("Home", Icons.Outlined.Home, AppNavRoutes.Home.route),
    BottomNavItem("Search", Icons.Outlined.Search, AppNavRoutes.Search.route),
    BottomNavItem("Library", Icons.Outlined.Bookmarks, AppNavRoutes.Library.route),
    BottomNavItem("Profile", Icons.Outlined.Person, AppNavRoutes.Profile.route)
)
