package com.github.af2905.movieland.presentation.base

import androidx.lifecycle.ViewModel
import com.github.af2905.movieland.helper.coroutine.CoroutineDispatcherProvider

abstract class BaseViewModel<STATE : UiState, EFFECT : UiEffect>(
    val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {
}
   /* protected val container: Container<STATE, EFFECT> = Container(viewModelScope)
    abstract fun showError(error: UiText)

    open fun handleError(throwable: Throwable) {
        Timber.e(throwable)
        when (throwable) {
            is ConnectionException -> showError(UiText.of(R.string.fail_to_connect))
            is ApiException -> throwable.message?.let { showError(UiText.of(it)) }
        }
    } //вынести в отдельный класс

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

}*/