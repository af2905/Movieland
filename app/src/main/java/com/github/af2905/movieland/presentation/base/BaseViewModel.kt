package com.github.af2905.movieland.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.data.result.Result
import com.github.af2905.movieland.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.helper.navigator.Navigator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber

abstract class BaseViewModel<N : Navigator>(val coroutineDispatcherProvider: CoroutineDispatcherProvider) :
    ViewModel() {

    private val _navigator = MutableSharedFlow<((N) -> Unit)>()
    val navigator = _navigator.asSharedFlow()

    private var navigatorCollection: Job? = null

    protected fun navigate(invoke: N.() -> Unit) {
        viewModelScope.launch(coroutineDispatcherProvider.main()) {
            _navigator.emit { navigator ->
                navigator.invoke()
            }
        }
    }

    fun subscribeNavigator(collector: suspend ((N) -> Unit) -> Unit) {
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

    fun launch(dispatcher: CoroutineDispatcher, action: suspend () -> Unit) {
        viewModelScope.launch(dispatcher) {
            try {
                action()
            } catch (throwable: Throwable) {
                handleError(throwable)
            }
        }
    }

    fun launchUI(action: suspend () -> Unit) {
        launch(Dispatchers.Main) {
            action()
        }
    }

    fun launchIO(action: suspend () -> Unit) {
        launch(Dispatchers.IO) {
            action()
        }
    }

    open fun handleError(throwable: Throwable) {
        Timber.e(throwable)
        when (throwable) {

        }
    }
}