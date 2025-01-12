package com.github.af2905.movieland

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.github.af2905.movieland.navigation.AppBottomNavigation
import com.github.af2905.movieland.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

@Composable
fun MainApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { AppBottomNavigation(navController) }
    ) { paddingValues ->
        AppNavigation(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainApp()
        }
    }
}
