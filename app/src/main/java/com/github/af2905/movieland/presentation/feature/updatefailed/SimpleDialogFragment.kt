package com.github.af2905.movieland.presentation.feature.updatefailed

import androidx.navigation.NavController
import com.github.af2905.movieland.R
import com.github.af2905.movieland.databinding.FragmentSimpleDialogBinding
import com.github.af2905.movieland.helper.navigator.AppNavigator
import com.github.af2905.movieland.presentation.base.BaseFragment

class SimpleDialogFragment :
    BaseFragment<AppNavigator, FragmentSimpleDialogBinding, SimpleDialogViewModel>() {

    override fun getNavigator(navController: NavController) = AppNavigator(navController)
    override fun layoutRes(): Int = R.layout.fragment_simple_dialog
    override fun viewModelClass(): Class<SimpleDialogViewModel> = SimpleDialogViewModel::class.java

}