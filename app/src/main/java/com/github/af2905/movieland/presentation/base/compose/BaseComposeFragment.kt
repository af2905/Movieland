@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.github.af2905.movieland.presentation.base.compose

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.github.af2905.movieland.di.ViewModelFactory
import com.github.af2905.movieland.helper.navigator.Navigator
import com.github.af2905.movieland.presentation.base.Base
import com.github.af2905.movieland.presentation.base.BaseImpl
import com.github.af2905.movieland.presentation.base.UiEffect
import timber.log.Timber
import javax.inject.Inject

abstract class BaseComposeFragment<NV : Navigator, VM : ViewModel> : Fragment(), Base<NV> {

    protected abstract fun viewModelClass(): Class<VM>
    protected abstract fun getNavigator(navController: NavController): NV

    lateinit var viewModel: VM

    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory<VM>

    lateinit var navController: NavController
    lateinit var base: Base<NV>
    override val navigator: NV get() = base.navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[viewModelClass()]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = this.findNavController()
        base = BaseImpl(
            navigatorFactory = { getNavigator(navController) },
            requireContext = { requireContext() }
        )
    }

    override fun handleEffect(effect: UiEffect) {
        Timber.d("effect: ${effect}, class: $javaClass")
        base.handleEffect(effect)
    }
}