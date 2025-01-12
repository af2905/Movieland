package com.github.af2905.movieland.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.github.af2905.movieland.compose.MovieDetailsScreen
import com.github.af2905.movieland.compose.PersonDetailsScreen
import com.github.af2905.movieland.compose.TVShowDetailsScreen
import com.github.af2905.movieland.compose.theme.AppTheme
import com.github.af2905.movieland.core.compose.AppNavRoutes
import com.github.af2905.movieland.home.presentation.compose.HomeScreen
import com.github.af2905.movieland.liked.presentation.compose.LibraryScreen
import com.github.af2905.movieland.profile.presentation.compose.ProfileScreen
import com.github.af2905.movieland.search.compose.SearchScreen

@Composable
fun AppBottomNavigation(navController: NavHostController) {
    var selectedTab by rememberSaveable { mutableStateOf(AppNavRoutes.Home.route) }
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.route) },
                label = { Text(item.route.replaceFirstChar { it.uppercaseChar() }) },
                selected = selectedTab == item.route,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AppTheme.colors.theme.tint,
                    unselectedIconColor = AppTheme.colors.type.secondary,
                    selectedTextColor = AppTheme.colors.theme.tint,
                    unselectedTextColor = AppTheme.colors.type.secondary
                ),
                onClick = {
                    if (selectedTab == item.route) {
                        // Already in this tab: Clear stack and go to main screen
                        navController.navigate(item.route) {
                            popUpTo(item.route) { inclusive = true }
                        }
                    } else {
                        // Switching to a new tab: Update selectedTab and navigate
                        selectedTab = item.route
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = AppNavRoutes.Home.route,
        modifier = modifier
    ) {
        // Bottom Navigation Destinations
        composable(AppNavRoutes.Home.route) { HomeScreen(navController) }
        composable(AppNavRoutes.Search.route) { SearchScreen(navController) }
        composable(AppNavRoutes.Library.route) { LibraryScreen(navController) }
        composable(AppNavRoutes.Profile.route) { ProfileScreen(navController) }

        // Global Detail Screens
        composable(
            route = AppNavRoutes.MovieDetails.route,
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId")
            itemId?.let { MovieDetailsScreen(itemId, navController) }

        }
        composable(
            route = AppNavRoutes.PersonDetails.route,
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId")
            itemId?.let { PersonDetailsScreen(itemId) }
        }
        composable(
            route = AppNavRoutes.TVShowDetails.route,
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId")
            itemId?.let { TVShowDetailsScreen(itemId) }
        }
    }
}
