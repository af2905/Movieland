package com.github.af2905.movieland.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.data.result.ApiException
import com.github.af2905.movieland.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.helper.navigator.Navigator
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<N : Navigator>(val coroutineDispatcherProvider: CoroutineDispatcherProvider) :
    ViewModel() {

    private val _navigator = MutableSharedFlow<((N) -> Unit)>()
    val navigator = _navigator.asSharedFlow()

    private val _exceptionMessage = MutableStateFlow("")
    val exceptionMessage = _exceptionMessage

    private val _failedToConnect = MutableStateFlow(Unit)
    val failedToConnect = _failedToConnect

    private val _loading = MutableStateFlow(false)
    val loading = _loading

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

    open fun handleError(throwable: Throwable) {
        Timber.e(throwable)
        when (throwable) {
            is ApiException.ConnectionException -> _failedToConnect.tryEmit(Unit)
            is ApiException -> throwable.message?.let { _exceptionMessage.tryEmit(it) }
        }
    }

    private fun launch(dispatcher: CoroutineDispatcher, action: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(dispatcher) {
            try {
                action()
            } catch (throwable: Throwable) {
                handleError(throwable)
            }
        }
    }

    fun launchUI(action: suspend CoroutineScope.() -> Unit) {
        launch(coroutineDispatcherProvider.main(), action)
    }

    fun launchIO(action: suspend CoroutineScope.() -> Unit) {
        launch(coroutineDispatcherProvider.io(), action)
    }

    private fun <T> CoroutineScope.internalAsync(
        context: CoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        action: suspend CoroutineScope.() -> T
    ): Deferred<Result<T>> {
        return async(context, start) {
            try {
                Result.success(action())
            } catch (throwable: Throwable) {
                Result.failure(throwable)
            }
        }
    }

    fun <T> CoroutineScope.uIAsync(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        action: suspend CoroutineScope.() -> T
    ) = internalAsync(coroutineDispatcherProvider.main(), start, action)

    fun <T> CoroutineScope.iOAsync(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        action: suspend CoroutineScope.() -> T
    ) = internalAsync(coroutineDispatcherProvider.io(), start, action)
}