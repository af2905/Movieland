package com.github.af2905.movieland.core.data.error

import java.io.IOException

abstract class ApiException(message: String?) : IOException(message)

class UndefinedException(throwable: Throwable) : ApiException(throwable.message) {
    init {
        addSuppressed(throwable)
    }
}

class ConnectionException(message: String?) : ApiException(message)

open class ErrorBodyApiException(body: ErrorBody) : ApiException(body.error)

class ClientException(body: ErrorBody) : ErrorBodyApiException(body)
class ServerException(body: ErrorBody) : ErrorBodyApiException(body)
