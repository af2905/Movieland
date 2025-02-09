package com.github.af2905.movieland.search.presentation.result

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.af2905.movieland.core.compose.AppNavRoutes

@Composable
fun SearchResultNavWrapper(
    navController: NavHostController
) {
    val viewModel: SearchResultViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SearchResultEffect.NavigateToResults -> {

                }
            }
        }
    }

    SearchResultScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}
