package com.github.af2905.movieland.home.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.af2905.movieland.compose.theme.Themes

@Composable
fun HomeScreenNavWrapper(
    navController: NavHostController,
    isDarkTheme: Boolean,
    currentTheme: Themes,
    onDarkThemeClick: () -> Unit,
    onThemeClick: (Themes) -> Unit
) {
    val viewModel: HomeViewModel = hiltViewModel()





    HomeScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}


/*
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffect.NavigateToSettings -> {
                    navController.navigate(SettingsDestination)
                }

                is HomeEffect.NavigateToBookAppointment -> {
                    navController.navigate(AppointmentBookingDestination)
                }

                is HomeEffect.NavigateToUpcomingAppointments -> {
                    navController.navigate(NavGraphs.appointment.startAppDestination as DirectionDestination)
                }

                is HomeEffect.NavigateToMedicalDataItem -> {
                    effect.navigationDestination?.let { destination ->
                        navController.navigate(destination)
                    }
                }

                is HomeEffect.NavigateToAdditionalFeaturesItem -> {
                    effect.navigationDestination?.let { destination ->
                        navController.navigate(destination)
                    }
                }

                is HomeEffect.NavigateToAppointmentDetail -> {
                    navController.navigate(
                        AppointmentDetailsDestination(
                            clinicId = effect.clinicId,
                            appointmentId = effect.appointmentId,
                            appointmentMode = effect.appointmentMode
                        )
                    )
                }

                is HomeEffect.NavigateToNotifications -> {
                    //TODO not implemented yet
                }
            }
        }
    }
*/