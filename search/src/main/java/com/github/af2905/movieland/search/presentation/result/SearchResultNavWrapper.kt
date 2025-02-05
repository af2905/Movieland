package com.github.af2905.movieland.search.presentation.result

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun SearchResultNavWrapper(
    navController: NavHostController
) {
    val viewModel: SearchResultViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SearchResultEffect.NavigateToResults -> {
                    // TODO: Navigate to search results screen
                }
            }
        }
    }

    SearchResultScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}
