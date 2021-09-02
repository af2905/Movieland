package com.github.af2905.movieland.presentation.base

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
import com.github.af2905.movieland.di.ViewModelFactory
import com.github.af2905.movieland.helper.navigator.Navigator
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<NV : Navigator, DB : ViewDataBinding, VM : BaseViewModel<NV>> :
    DaggerFragment() {

    protected abstract fun layoutRes(): Int
    protected abstract fun viewModelClass(): Class<VM>
    protected abstract fun getNavigator(navController: NavController): NV

    lateinit var binding: DB
    lateinit var viewModel: VM

    /*@Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory*/

    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory<VM>


    /*

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(viewModelClass)
        viewModel.exceptionMessage.observe(this, Observer {
            SimpleDialogFragment.newInstance(
                message = it,
                closeButtonRes = R.string.any_screen_close
            ).show(parentFragmentManager, SimpleDialogFragment.TAG)
        })*/

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = this.findNavController()
        viewModel.subscribeNavigator { navigate -> navigate(getNavigator(navController)) }
    }

    open fun onBind() {}

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }
}