package com.github.af2905.movieland.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.R
import com.github.af2905.movieland.data.error.ApiException
import com.github.af2905.movieland.data.error.ConnectionException
import com.github.af2905.movieland.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.helper.extension.launchCollect
import com.github.af2905.movieland.helper.navigator.Navigator
import com.github.af2905.movieland.helper.text.UIText
import com.github.af2905.movieland.helper.text.of
import com.github.af2905.movieland.presentation.common.effect.Navigate
import com.github.af2905.movieland.presentation.common.effect.ToastMessage
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<N : Navigator>(val coroutineDispatcherProvider: CoroutineDispatcherProvider) :
    ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading

    protected val _effect = MutableSharedFlow<UIEffect>()
    val effect = _effect.asSharedFlow()

    private var effectCollection: Job? = null
    fun subscribeOnEffect(collector: suspend (UIEffect) -> Unit) {
        effectCollection?.cancel()
        effectCollection = effect.launchCollect(viewModelScope, collector)
    }

    fun navigator(invoke: N.() -> Unit) {
        launch(coroutineDispatcherProvider.main()) {
            _effect.emit(
                Navigate { navigator ->
                    @Suppress("UNCHECKED_CAST")
                    (navigator as N).invoke()
                }
            )
        }
    }

    private fun showError(error: UIText) {
        launch(coroutineDispatcherProvider.main()) { _effect.emit(ToastMessage(error)) }
    }

    open fun handleError(throwable: Throwable) {
        Timber.e(throwable)
        when (throwable) {
            is ConnectionException -> showError(UIText.of(R.string.fail_to_connect))
            is ApiException -> throwable.message?.let { showError(UIText.of(it)) }
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