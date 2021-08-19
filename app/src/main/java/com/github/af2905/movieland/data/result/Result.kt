package com.github.af2905.movieland.data.result

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    val extractData: R?
        get() = when (this) {
            is Success -> data
            is Error -> null
        }
}

inline fun <T> Result<T>.getOrThrow(): T {
    return when (this) {
        is Result.Success -> data
        is Result.Error -> throw exception
    }
}