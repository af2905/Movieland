package com.github.af2905.movieland.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.github.af2905.movieland.compose.MovieDetailsScreen
import com.github.af2905.movieland.compose.PersonDetailsScreen
import com.github.af2905.movieland.compose.TVShowDetailsScreen
import com.github.af2905.movieland.compose.theme.Themes
import com.github.af2905.movieland.core.compose.AppNavRoutes
import com.github.af2905.movieland.home.presentation.compose.HomeScreen
import com.github.af2905.movieland.liked.presentation.compose.LibraryScreen
import com.github.af2905.movieland.profile.presentation.compose.ProfileScreen
import com.github.af2905.movieland.search.compose.SearchScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    currentTheme: Themes,
    onDarkThemeClick: () -> Unit,
    onThemeClick: (Themes) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = AppNavRoutes.Home.route,
        modifier = modifier
    ) {
        // Home Tab
        navigation(startDestination = "home/main", route = AppNavRoutes.Home.route) {
            composable("home/main") {
                HomeScreen(
                    navController = navController,
                    isDarkTheme = isDarkTheme,
                    currentTheme = currentTheme,
                    onDarkThemeClick = onDarkThemeClick,
                    onThemeClick = onThemeClick
                )
            }
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

        composable(AppNavRoutes.Search.route) { SearchScreen(navController) }
        composable(AppNavRoutes.Library.route) { LibraryScreen(navController) }
        composable(AppNavRoutes.Profile.route) { ProfileScreen(navController) }

    }
}

/*@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    currentTheme: Themes,
    onDarkThemeClick: () -> Unit,
    onThemeClick: (Themes) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = AppNavRoutes.Home.route,
        modifier = modifier
    ) {
        // Home Tab
        navigation(startDestination = "home/main", route = AppNavRoutes.Home.route) {
            composable("home/main") {
                HomeScreen(
                    navController = navController,
                    isDarkTheme = isDarkTheme,
                    currentTheme = currentTheme,
                    onDarkThemeClick = onDarkThemeClick,
                    onThemeClick = onThemeClick
                )
            }
            composable(
                route = AppNavRoutes.MovieDetails.route,
                arguments = listOf(navArgument("itemId") { type = NavType.IntType })
            ) { backStackEntry ->
                val itemId = backStackEntry.arguments?.getInt("itemId")
                itemId?.let { MovieDetailsScreen(itemId, navController) }
            }
        }

        // Search Tab
        navigation(startDestination = "search/main", route = AppNavRoutes.Search.route) {
            composable("search/main") { SearchScreen(navController) }
            composable(
                route = AppNavRoutes.MovieDetails.route,
                arguments = listOf(navArgument("itemId") { type = NavType.IntType })
            ) { backStackEntry ->
                val itemId = backStackEntry.arguments?.getInt("itemId")
                itemId?.let { MovieDetailsScreen(itemId, navController) }
            }
        }

        // Library Tab
        composable(AppNavRoutes.Library.route) { LibraryScreen(navController) }

        // Profile Tab
        composable(AppNavRoutes.Profile.route) { ProfileScreen(navController) }
    }
}
*/
