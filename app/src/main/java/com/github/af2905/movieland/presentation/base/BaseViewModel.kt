package com.github.af2905.movieland.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.data.error.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

abstract class BaseViewModel : ViewModel() {

    /*@Inject
    lateinit var coroutineDispatcherProvider: CoroutineDispatcherProvider*/
    val scope = viewModelScope

    private fun <P, R> launch(
        dispatcher: CoroutineDispatcher, params: P, execute: suspend (P) -> Result<R>, success : (R) -> Unit
    ) {
        scope.launch(dispatcher) {
            when (val result = execute(params)) {
                is Result.Success -> success(result.data)
                is Result.Error -> handleError<Result.Error>(result)
            }
        }
    }

    fun <P, R> launchIO(params: P, execute: suspend (P) -> Result<R>, success : (R) -> Unit) {
        launch(Dispatchers.IO, params, execute, success)
    }

    fun <P, R> launchMain(params: P, execute: suspend (P) -> Result<R>, success : (R) -> Unit) {
        launch(Dispatchers.Main, params, execute, success)
    }

    //todo add error handling
    fun <R> handleError(result: Result.Error) {
        val exception = result.exception

        if (exception is HttpException) {

        }
    }
}