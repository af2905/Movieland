package com.github.af2905.movieland.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.data.error.Result
import com.github.af2905.movieland.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.helper.navigator.Navigator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException

abstract class BaseViewModel<N : Navigator>(val coroutineDispatcherProvider: CoroutineDispatcherProvider) :
    ViewModel() {

    private val _navigator = MutableSharedFlow<((Navigator) -> Unit)>()
    val navigator = _navigator.asSharedFlow()

    private var navigatorCollection: Job? = null

    protected fun navigate(invoke: N.() -> Unit) {
        viewModelScope.launch(coroutineDispatcherProvider.main()) {
            _navigator.emit { navigator ->
                @Suppress("UNCHECKED_CAST")
                (navigator as N).invoke()
            }
        }
    }

    fun subscribeNavigator(collector: suspend ((Navigator) -> Unit) -> Unit) {
        navigatorCollection?.cancel()
        navigatorCollection = viewModelScope.launch(coroutineDispatcherProvider.main()) {
            navigator.collect { collector(it) }
        }
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