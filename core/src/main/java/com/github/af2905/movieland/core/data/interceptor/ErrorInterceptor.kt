package com.github.af2905.movieland.core.data.interceptor

import com.github.af2905.movieland.core.data.error.*
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ErrorInterceptor @Inject constructor(private val gson: Gson) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            return handledResponse(chain)
        } catch (apiException: ApiException) {
            throw apiException
        } catch (e: Exception) {
            if (e is ConnectException || e is UnknownHostException || e is SocketTimeoutException) {
                throw ConnectionException(e.message)
            }
            throw UndefinedException(e)
        }
    }

    private fun handledResponse(chain: Interceptor.Chain): Response {

        val response = chain.proceed(chain.request())
        if (response.isSuccessful) {
            return response
        }

        val errorBody = gson.fromJson(response.body?.string(), ErrorBody::class.java)

        when (response.code) {

            in HttpResponseCode.SERVER_ERROR.code -> throw ServerException(errorBody)

            in HttpResponseCode.BAD_REQUEST.code,
            in HttpResponseCode.CLIENT_ERROR.code -> throw ClientException(errorBody)

            else -> throw IllegalStateException(
                "Unexpected response with code: ${response.code} and body: ${response.body}"
            )
        }
    }
}