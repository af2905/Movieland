package com.github.af2905.movieland

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.github.af2905.movieland.compose.components.navigation.AppBottomNavigationView
import com.github.af2905.movieland.compose.theme.AppTheme
import com.github.af2905.movieland.compose.theme.Themes
import com.github.af2905.movieland.core.compose.AppNavRoutes
import com.github.af2905.movieland.navigation.AppNavigation
import com.github.af2905.movieland.navigation.bottomNavItems
import dagger.hilt.android.AndroidEntryPoint

@Composable
fun MainApp(
    isDarkTheme: Boolean,
    currentTheme: Themes,
    onDarkThemeClick: () -> Unit,
    onThemeClick: (Themes) -> Unit
) {
    val navController = rememberNavController()

    // Track the current selected tab
    val currentTab = rememberSaveable { mutableStateOf(AppNavRoutes.Home.route) }
    val activity = LocalContext.current as? Activity

    BackHandler {
        handleBackPress(navController, currentTab, activity)
    }

    Scaffold(
        modifier = Modifier.background(AppTheme.colors.theme.tintBg),
        bottomBar = {
            AppBottomNavigationView(
                navController = navController,
                items = bottomNavItems,
                currentTab = currentTab.value,
                onTabSelected = { tab ->
                    currentTab.value = tab
                    navController.navigate(tab) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        },
        contentColor = AppTheme.colors.theme.tintGhost,
        containerColor = AppTheme.colors.theme.tintBg
    ) { paddingValues ->
        AppNavigation(
            navController = navController,
            modifier = Modifier.padding(paddingValues),
            isDarkTheme = isDarkTheme,
            currentTheme = currentTheme,
            onDarkThemeClick = onDarkThemeClick,
            onThemeClick = onThemeClick
        )
    }
}

fun handleBackPress(navController: NavController, currentTab: MutableState<String>, activity: Activity?) {
    when (currentTab.value) {
        AppNavRoutes.Search.route,
        AppNavRoutes.Library.route,
        AppNavRoutes.Profile.route -> {
            // If on a non-home root screen, navigate to Home
            currentTab.value = AppNavRoutes.Home.route
            navController.navigate(AppNavRoutes.Home.route) {
                popUpTo(navController.graph.startDestinationId) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }

        AppNavRoutes.Home.route -> {
            // If on Home root screen, close the app
            activity?.finish()
        }

        else -> {
            // Otherwise, pop the back stack
            navController.popBackStack()
        }
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var currentTheme by rememberSaveable { mutableStateOf(Themes.MELODRAMA) }
            var darkTheme by rememberSaveable { mutableStateOf(false) }
            val currentPalette by remember(currentTheme) { mutableStateOf(currentTheme.getTheme()) }

            AppTheme(
                palette = currentPalette,
                darkTheme = darkTheme
            ) {
                MainApp(
                    isDarkTheme = darkTheme,
                    currentTheme = currentTheme,
                    onDarkThemeClick = { darkTheme = !darkTheme },
                    onThemeClick = { theme -> currentTheme = theme }
                )
            }
        }
    }
}
