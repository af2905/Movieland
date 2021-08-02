package com.github.af2905.movieland.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.data.error.Result
import com.github.af2905.movieland.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.helper.navigator.Navigator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import retrofit2.HttpException

abstract class BaseViewModel<N : Navigator>(val coroutineDispatcherProvider: CoroutineDispatcherProvider) :
    ViewModel() {

    private val _navigator = MutableLiveData<((Navigator) -> Unit)>()
    val navigator: LiveData<((Navigator) -> Unit)> = _navigator

    protected fun navigate(start: N.() -> Unit) {
        _navigator.postValue { navigator -> (navigator as N).start() }
    }

    private fun <P, R> launch(
        dispatcher: CoroutineDispatcher, params: P,
        execute: suspend (P) -> Result<R>, success: (R) -> Unit
    ) {
        viewModelScope.launch(dispatcher) {
            when (val result = execute(params)) {
                is Result.Success -> success(result.data)
                is Result.Error -> handleError<Result.Error>(result)
            }
        }
    }

    fun <P, R> launchIO(params: P, execute: suspend (P) -> Result<R>, success: (R) -> Unit) {
        launch(coroutineDispatcherProvider.io(), params, execute, success)
    }

    fun <P, R> launchMain(params: P, execute: suspend (P) -> Result<R>, success: (R) -> Unit) {
        launch(coroutineDispatcherProvider.main(), params, execute, success)
    }

    //todo add error handling
    fun <R> handleError(result: Result.Error) {
        val exception = result.exception

        if (exception is HttpException) {

        }
    }
}