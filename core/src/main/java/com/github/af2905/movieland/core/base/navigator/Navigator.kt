package com.github.af2905.movieland.core.base.navigator

import androidx.navigation.NavController

abstract class Navigator(protected val navController: NavController) {

    fun back() = navController.navigateUp()

}