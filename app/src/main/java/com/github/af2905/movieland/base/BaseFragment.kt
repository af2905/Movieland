package com.github.af2905.movieland.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.af2905.movieland.BR
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment<DB : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    protected abstract fun layoutRes(): Int
    protected abstract fun viewModelClass(): Class<VM>

    lateinit var binding: DB
    lateinit var viewModel: VM

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

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

    open fun onBind() {}

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }
}