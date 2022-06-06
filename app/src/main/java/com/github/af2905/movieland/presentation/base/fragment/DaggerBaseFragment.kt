@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.github.af2905.movieland.presentation.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.github.af2905.movieland.BR
import com.github.af2905.movieland.di.ViewModelFactory
import com.github.af2905.movieland.helper.navigator.Navigator
import com.github.af2905.movieland.presentation.base.Base
import com.github.af2905.movieland.presentation.base.BaseImpl
import com.github.af2905.movieland.presentation.base.UiEffect
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

abstract class DaggerBaseFragment<NV : Navigator, DB : ViewDataBinding, VM : ViewModel> :
    DaggerFragment(), Base<NV> {

    protected abstract fun layoutRes(): Int
    protected abstract fun viewModelClass(): Class<VM>
    protected abstract fun getNavigator(navController: NavController): NV

    lateinit var binding: DB
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes(), container, false)
        binding.let {
            it.setVariable(BR.viewModel, viewModel)
            it.lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = this.findNavController()
        base = BaseImpl(
            navigatorFactory = { getNavigator(navController) },
            requireContext = { requireContext() }
        )
        onBind()
    }

    override fun handleEffect(effect: UiEffect) {
        Timber.d("effect: ${effect}, class: $javaClass")
        base.handleEffect(effect)
    }

    open fun onBind() {}

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }
}