package com.github.af2905.movieland.core.common.network

sealed class ResultWrapper<out T> {
    data class Success<T>(val data: T) : ResultWrapper<T>()
    data class Error(val message: String, val throwable: Throwable? = null) : ResultWrapper<Nothing>()
    data object Loading : ResultWrapper<Nothing>()
}