package com.github.af2905.movieland.data.error

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()

    val extractData: R?
        get() = when (this) {
            is Success -> data
            is Error -> null
            is Loading -> null
        }
}