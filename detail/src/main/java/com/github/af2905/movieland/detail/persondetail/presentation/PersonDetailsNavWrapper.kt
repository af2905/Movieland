package com.github.af2905.movieland.detail.persondetail.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.af2905.movieland.core.compose.AppNavRoutes
import com.github.af2905.movieland.core.data.database.entity.MediaType
import com.github.af2905.movieland.detail.persondetail.presentation.screen.PersonDetailsScreen
import com.github.af2905.movieland.detail.persondetail.presentation.state.PersonDetailsEffect
import com.github.af2905.movieland.detail.persondetail.presentation.viewmodel.PersonDetailsViewModel

@Composable
fun PersonDetailsNavWrapper(
    personId: Int,
    navController: NavHostController
) {
    val viewModel = hiltViewModel<PersonDetailsViewModel, PersonDetailsViewModel.Factory>(
        creationCallback = { factory ->
            factory.create(personId)
        }
    )

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is PersonDetailsEffect.NavigateBack -> {
                    navController.popBackStack()
                }

                is PersonDetailsEffect.NavigateToCredit -> {
                    when (effect.type) {
                        MediaType.MOVIE -> {
                            navController.navigate(
                                AppNavRoutes.MovieDetails.createRoute(effect.creditId)
                            )
                        }

                        MediaType.TV -> {
                            navController.navigate(
                                AppNavRoutes.TVShowDetails.createRoute(effect.creditId)
                            )
                        }

                        else -> Unit
                    }
                }
            }
        }
    }

    PersonDetailsScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}
