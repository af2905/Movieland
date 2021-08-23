package com.github.af2905.movieland.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.data.result.ApiException
import com.github.af2905.movieland.data.result.ConnectionException
import com.github.af2905.movieland.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.helper.navigator.Navigator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel<N : Navigator>(val coroutineDispatcherProvider: CoroutineDispatcherProvider) :
    ViewModel() {

    private val _navigator = MutableSharedFlow<((N) -> Unit)>()
    val navigator = _navigator.asSharedFlow()

    private val _exceptionMessage = MutableStateFlow("")
    val exceptionMessage = _exceptionMessage

    private val _failedToConnect = MutableStateFlow(Unit)
    val failedToConnect = _failedToConnect

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

    open fun handleError(throwable: Throwable) {
        Timber.e(throwable)
        when (throwable) {
            is ConnectionException -> _failedToConnect.tryEmit(Unit)
            is ApiException -> throwable.message?.let { _exceptionMessage.tryEmit(it) }
        }
    }
}