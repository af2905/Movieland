package com.github.af2905.movieland.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.data.error.Result
import com.github.af2905.movieland.helper.ICoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    lateinit var coroutineDispatcherProvider: ICoroutineDispatcherProvider
    val scope = viewModelScope

    private fun <P, R> launch(
        dispatcher: CoroutineDispatcher, params: P, execute: suspend (P) -> Result<R>
    ) {
        scope.launch(dispatcher) {
            when (val result = execute(params)) {
                is Result.Success -> Result.Success(result.data)
                is Result.Error -> handleError<Result.Error>(result)
            }
        }
    }

    fun <P, R> launchIO(params: P, execute: suspend (P) -> Result<R>) {
        launch(coroutineDispatcherProvider.io(), params, execute)
    }

    fun <P, R> launchMain(params: P, execute: suspend (P) -> Result<R>) {
        launch(coroutineDispatcherProvider.main(), params, execute)
    }

    //todo add error handling
    fun <R> handleError(result: Result.Error) {
        val exception = result.exception

        if (exception is HttpException) {

        }
    }
}