package com.github.af2905.movieland.core.data.interceptor

import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

class HttpLoggerInterceptor : HttpLoggingInterceptor.Logger {

    override fun log(message: String) = Timber.tag("OkHttp").d(message)

}