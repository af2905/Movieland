package com.github.af2905.movieland.di.module

import com.github.af2905.movieland.BuildConfig
import com.github.af2905.movieland.data.api.MoviesApi
import com.github.af2905.movieland.data.api.PeopleApi
import com.github.af2905.movieland.data.api.SearchApi
import com.github.af2905.movieland.data.interceptor.ApiKeyInterceptor
import com.github.af2905.movieland.data.interceptor.ErrorInterceptor
import com.github.af2905.movieland.data.interceptor.HttpLoggerInterceptor
import com.github.af2905.movieland.di.scope.AppScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val DEFAULT_TIMEOUT = 30L
const val MAX_IDLE_CONNECTION = 5
const val KEEP_ALIVE_DURATION = 30L

@Module
class NetworkModule {

    @AppScope
    @Provides
    fun providePeopleApi(retrofit: Retrofit): PeopleApi = retrofit.create(PeopleApi::class.java)

    @AppScope
    @Provides
    fun provideSearchApi(retrofit: Retrofit): SearchApi = retrofit.create(SearchApi::class.java)

    @AppScope
    @Provides
    fun provideMoviesApi(retrofit: Retrofit): MoviesApi = retrofit.create(MoviesApi::class.java)

    @AppScope
    @Provides
    fun provideRetrofitClient(client: OkHttpClient, converter: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(converter)
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @AppScope
    @Provides
    fun provideConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @AppScope
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @AppScope
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        apiKeyInterceptor: ApiKeyInterceptor,
        errorInterceptor: ErrorInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectionPool(
                ConnectionPool(MAX_IDLE_CONNECTION, KEEP_ALIVE_DURATION, TimeUnit.SECONDS)
            )
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(errorInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @AppScope
    @Provides
    fun provideApiKeyInterceptor(): ApiKeyInterceptor = ApiKeyInterceptor()

    @AppScope
    @Provides
    fun provideErrorInterceptor(gson: Gson): ErrorInterceptor = ErrorInterceptor(gson)

    @AppScope
    @Provides
    fun provideHttpLoggingInterceptor(logger: HttpLoggingInterceptor.Logger): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(logger).setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @AppScope
    @Provides
    fun provideHttpLoggerInterceptor(): HttpLoggingInterceptor.Logger = HttpLoggerInterceptor()
}

