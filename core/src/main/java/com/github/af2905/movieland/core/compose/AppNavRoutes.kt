package com.github.af2905.movieland.core.compose

sealed class AppNavRoutes(val route: String) {
    data object Home : AppNavRoutes("home")
    data object Search : AppNavRoutes("search")
    data object Library : AppNavRoutes("library")
    data object Profile : AppNavRoutes("profile")

    data object MovieDetails : AppNavRoutes("movieDetails/{itemId}") {
        fun createRoute(itemId: Int) = "movieDetails/$itemId"
    }
    data object PersonDetails : AppNavRoutes("personDetails/{itemId}") {
        fun createRoute(itemId: Int) = "personDetails/$itemId"
    }
    data object TVShowDetails : AppNavRoutes("tvShowDetails/{itemId}") {
        fun createRoute(itemId: Int) = "tvShowDetails/$itemId"
    }
}