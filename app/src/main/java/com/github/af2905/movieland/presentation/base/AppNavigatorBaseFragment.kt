package com.github.af2905.movieland.presentation.base

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.github.af2905.movieland.helper.navigator.AppNavigator
import com.github.af2905.movieland.presentation.base.fragment.DaggerBaseFragment

abstract class AppNavigatorBaseFragment< DB : ViewDataBinding, VM : ViewModel>
    : DaggerBaseFragment<AppNavigator, DB, VM>() {
    override fun getNavigator(navController: NavController) = AppNavigator(navController)
}