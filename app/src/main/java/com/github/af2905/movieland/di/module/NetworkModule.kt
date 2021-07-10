package com.github.af2905.movieland.di.module

import com.github.af2905.movieland.BuildConfig
import com.github.af2905.movieland.data.api.MoviesApi
import com.github.af2905.movieland.data.interceptor.ApiKeyInterceptor
import com.github.af2905.movieland.data.interceptor.HttpLoggerInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val DEFAULT_TIMEOUT = 30L
const val MAX_IDLE_CONNECTION = 5
const val KEEP_ALIVE_DURATION = 30L

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideMoviesApi(retrofit: Retrofit): MoviesApi = retrofit.create(MoviesApi::class.java)

    @Singleton
    @Provides
    fun provideRetrofitClient(client: OkHttpClient, converter: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(converter)
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create(GsonBuilder().create())

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor, apiKeyInterceptor: ApiKeyInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectionPool(
                ConnectionPool(
                    MAX_IDLE_CONNECTION, KEEP_ALIVE_DURATION, TimeUnit.SECONDS
                )
            )
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiKeyInterceptor(): ApiKeyInterceptor = ApiKeyInterceptor()

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(logger: HttpLoggingInterceptor.Logger): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(logger).setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideHttpLoggerInterceptor(): HttpLoggingInterceptor.Logger = HttpLoggerInterceptor()
}

