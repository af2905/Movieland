package com.github.af2905.movieland.data.interceptor

import com.github.af2905.movieland.BuildConfig
import com.github.af2905.movieland.data.ApiParams
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter(ApiParams.API_KEY, BuildConfig.THE_MOVIE_DATABASE_API_KEY).build()
        val newRequest = request.newBuilder().url(url).build()
        return chain.proceed(newRequest)
    }
}