package com.github.af2905.movieland.presentation.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.github.af2905.movieland.BR
import com.github.af2905.movieland.helper.navigator.AppNavigator
import com.github.af2905.movieland.helper.navigator.Navigator
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<DB : ViewDataBinding, VM : BaseViewModel<out Navigator>>(
    getNavigator: ((NavController) -> Navigator)? = null
) : DaggerFragment() {

    protected abstract fun layoutRes(): Int
    protected abstract fun viewModelClass(): Class<VM>
    private val navigator: Navigator by lazy {
        getNavigator?.invoke(navController) ?: AppNavigator(navController)
    }

    lateinit var binding: DB
    lateinit var viewModel: VM

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(viewModelClass())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes(), container, false)
        binding.let {
            it.setVariable(BR.viewModel, viewModel)
            it.lifecycleOwner = viewLifecycleOwner
            it.executePendingBindings()
        }
        onBind()
        return binding.root
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = this.findNavController()
        viewModel.subscribeNavigator { navigate -> navigate(navigator) }
    }

    open fun onBind() {}

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }
}