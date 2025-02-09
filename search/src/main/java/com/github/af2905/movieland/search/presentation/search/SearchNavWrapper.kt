package com.github.af2905.movieland.search.presentation.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.af2905.movieland.core.compose.AppNavRoutes

@Composable
fun SearchNavWrapper(
    navController: NavHostController
) {
    val viewModel: SearchViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SearchEffect.NavigateToResults -> {
                    navController.navigate(AppNavRoutes.SearchResult.route)
                }
            }
        }
    }

    SearchScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}
