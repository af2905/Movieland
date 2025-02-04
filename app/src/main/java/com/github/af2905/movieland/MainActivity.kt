package com.github.af2905.movieland

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.github.af2905.movieland.core.data.datastore.UserPreferences
import com.github.af2905.movieland.core.data.datastore.UserPreferencesManager
import com.github.af2905.movieland.navigation.AppNavigation
import com.github.af2905.movieland.navigation.bottomNavItems
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun MainApp(
    isDarkTheme: Boolean,
    currentTheme: Themes,
    onDarkThemeClick: () -> Unit,
    onThemeClick: (Themes) -> Unit
) {
    val navController = rememberNavController()
    val currentTab = rememberSaveable { mutableStateOf(AppNavRoutes.Home.route) }
    val activity = LocalContext.current as? Activity

    val currentDestination by navController.currentBackStackEntryFlow.collectAsState(initial = null)

    val showBottomBar = currentDestination?.destination?.route?.startsWith(AppNavRoutes.YouTubePlayer.route) != true

    BackHandler {
        handleBackPress(navController, currentTab, activity)
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
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
            }
        },
        contentColor = AppTheme.colors.theme.tintGhost,
        containerColor = AppTheme.colors.background.default
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
    val homeRoute = AppNavRoutes.Home.route

    if (currentTab.value != homeRoute) {
        currentTab.value = homeRoute
        navController.navigate(homeRoute) {
            popUpTo(navController.graph.startDestinationId) { inclusive = true }
            launchSingleTop = true
            restoreState = true
        }
    } else {
        activity?.finish()
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var userPreferencesManager: UserPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val scope = rememberCoroutineScope()
            val userPreferences by userPreferencesManager.userPreferencesFlow.collectAsState(initial = UserPreferences())

            var currentTheme by rememberSaveable { mutableStateOf(Themes.FANTASY) }
            var darkTheme: Boolean? by rememberSaveable { mutableStateOf(null) }

            val systemDefaultDarkMode = isSystemInDarkTheme()

            LaunchedEffect(userPreferences) {
                currentTheme = Themes.entries.find { it.name == userPreferences.theme } ?: Themes.FANTASY
                darkTheme = if (userPreferences.isDarkMode) true else null // Null means use system default
            }

            val currentPalette by remember(currentTheme) { mutableStateOf(currentTheme.getTheme()) }
            val finalDarkTheme = darkTheme ?: systemDefaultDarkMode // Use system theme if no preference

            AppTheme(
                palette = currentPalette,
                darkTheme = finalDarkTheme
            ) {
                MainApp(
                    isDarkTheme = finalDarkTheme,
                    currentTheme = currentTheme,
                    onDarkThemeClick = {
                        val newDarkMode = !finalDarkTheme
                        darkTheme = newDarkMode
                        scope.launch { userPreferencesManager.updateDarkMode(newDarkMode) }
                    },
                    onThemeClick = { theme ->
                        currentTheme = theme
                        scope.launch { userPreferencesManager.updateTheme(theme.name) }
                    }
                )
            }
        }
    }
}
