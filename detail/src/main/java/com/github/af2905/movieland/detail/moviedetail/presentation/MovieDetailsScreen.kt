package com.github.af2905.movieland.detail.moviedetail.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.github.af2905.movieland.core.compose.AppNavRoutes

@Composable
fun MovieDetailsScreen(
    movieId: Int,
    navController: NavHostController
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Movie Details Screen for ID: $movieId")
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.navigate(AppNavRoutes.PersonDetails.createRoute(123)) // Example ID
        }) {
            Text(text = "Go to Person Details")
        }
    }
}