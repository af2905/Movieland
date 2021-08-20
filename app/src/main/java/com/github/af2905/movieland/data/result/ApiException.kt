package com.github.af2905.movieland.data.result

import java.io.IOException

sealed class ApiException(message: String?) : IOException(message) {
    class ClientException(val exceptionCode: Int, message: String?) : ApiException(message)
    class ServerException(val exceptionCode: Int, message: String? = null) : ApiException(message)
    class ConnectionException(message: String?) : ApiException(message)
    class UndefinedException(throwable: Throwable) : ApiException(throwable.message) {
        init {
            addSuppressed(throwable)
        }
    }
}