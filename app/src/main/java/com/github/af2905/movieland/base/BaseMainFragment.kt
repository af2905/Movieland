package com.github.af2905.movieland.base

import androidx.databinding.ViewDataBinding

abstract class BaseMainFragment<DB : ViewDataBinding, VM : BaseViewModel> : BaseFragment<DB, VM>() {

}