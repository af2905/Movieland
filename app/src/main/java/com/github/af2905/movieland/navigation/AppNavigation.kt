package com.github.af2905.movieland.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.github.af2905.movieland.compose.theme.Themes
import com.github.af2905.movieland.core.compose.AppNavRoutes
import com.github.af2905.movieland.detail.moviedetail.presentation.MovieDetailsScreen
import com.github.af2905.movieland.detail.persondetail.presentation.PersonDetailsScreen
import com.github.af2905.movieland.detail.tvshowdetail.presentation.TVShowDetailsScreen
import com.github.af2905.movieland.home.presentation.HomeScreenNavWrapper
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
        startDestination = "home/main",
        modifier = modifier
    ) {
        // Home Tab
        navigation(
            startDestination = AppNavRoutes.Home.route,
            route = "home/main"
        ) {
            composable(AppNavRoutes.Home.route) {
                HomeScreenNavWrapper(
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
                itemId?.let { PersonDetailsScreen(itemId, navController) }
            }
            composable(
                route = AppNavRoutes.TVShowDetails.route,
                arguments = listOf(navArgument("itemId") { type = NavType.IntType })
            ) { backStackEntry ->
                val itemId = backStackEntry.arguments?.getInt("itemId")
                itemId?.let { TVShowDetailsScreen(itemId, navController) }
            }
        }

        // Search Tab
        navigation(
            startDestination = AppNavRoutes.Search.route,
            route = "search/main"
        ) {
            composable(AppNavRoutes.Search.route) {
                SearchScreen(navController)
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
                itemId?.let { PersonDetailsScreen(itemId, navController) }
            }
            composable(
                route = AppNavRoutes.TVShowDetails.route,
                arguments = listOf(navArgument("itemId") { type = NavType.IntType })
            ) { backStackEntry ->
                val itemId = backStackEntry.arguments?.getInt("itemId")
                itemId?.let { TVShowDetailsScreen(itemId, navController) }
            }
        }

        // Library Tab
        navigation(
            startDestination = AppNavRoutes.Library.route,
            route = "library/main"
        ) {
            composable(AppNavRoutes.Library.route) {
                LibraryScreen(navController)
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
                itemId?.let { PersonDetailsScreen(itemId, navController) }
            }
            composable(
                route = AppNavRoutes.TVShowDetails.route,
                arguments = listOf(navArgument("itemId") { type = NavType.IntType })
            ) { backStackEntry ->
                val itemId = backStackEntry.arguments?.getInt("itemId")
                itemId?.let { TVShowDetailsScreen(itemId, navController) }
            }
        }

        // Profile Tab
        navigation(
            startDestination = AppNavRoutes.Profile.route,
            route = "profile/main"
        ) {
            composable(AppNavRoutes.Profile.route) { ProfileScreen(navController) }
        }
    }
}
