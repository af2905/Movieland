package com.github.af2905.movieland.core.data.interceptor

import com.github.af2905.movieland.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestWithAuthHeader = request.newBuilder()
            .header("Authorization", "Bearer ${BuildConfig.THE_MOVIE_DATABASE_API_KEY}").build()
        val url = requestWithAuthHeader.url.newBuilder()
            .addQueryParameter("api_key", BuildConfig.THE_MOVIE_DATABASE_API_KEY)
            .build()
        val newRequest = requestWithAuthHeader.newBuilder().url(url).build()
        return chain.proceed(newRequest)
    }
}