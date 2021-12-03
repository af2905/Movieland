package com.github.af2905.movieland.data.interceptor

import com.github.af2905.movieland.data.result.ApiException
import com.github.af2905.movieland.data.result.HttpResponseCode
import okhttp3.Interceptor
import okhttp3.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class HttpInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            return handledResponse(chain)
        } catch (apiException: ApiException) {
            throw apiException
        } catch (e: Exception) {
            if (e is ApiException.ConnectionException || e is UnknownHostException || e is SocketTimeoutException) {
                throw ApiException.ConnectionException(e.message)
            }
            throw ApiException.UndefinedException(e)
        }
    }

    private fun handledResponse(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return when (response.code) {
            in HttpResponseCode.OK.code -> response
            in HttpResponseCode.CLIENT_ERROR.code -> {
                throw ApiException.ClientException(response.code, response.message)
            }
            in HttpResponseCode.SERVER_ERROR.code -> {
                throw ApiException.ServerException(response.code, response.message)
            }
            else -> throw IllegalStateException(
                "Unexpected response with code: ${response.code} and body: ${response.body}"
            )
        }
    }
}