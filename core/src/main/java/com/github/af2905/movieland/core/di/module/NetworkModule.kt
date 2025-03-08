package com.github.af2905.movieland.core.di.module

import com.github.af2905.movieland.core.BuildConfig
import com.github.af2905.movieland.core.data.api.DiscoverApi
import com.github.af2905.movieland.core.data.api.GenresApi
import com.github.af2905.movieland.core.data.api.MoviesApi
import com.github.af2905.movieland.core.data.api.PeopleApi
import com.github.af2905.movieland.core.data.api.SearchApi
import com.github.af2905.movieland.core.data.api.TrendingApi
import com.github.af2905.movieland.core.data.api.TvShowsApi
import com.github.af2905.movieland.core.data.interceptor.ApiKeyInterceptor
import com.github.af2905.movieland.core.data.interceptor.ErrorInterceptor
import com.github.af2905.movieland.core.data.interceptor.HttpLoggerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providePeopleApi(retrofit: Retrofit): PeopleApi =
        retrofit.create(PeopleApi::class.java)

    @Singleton
    @Provides
    fun provideSearchApi(retrofit: Retrofit): SearchApi =
        retrofit.create(SearchApi::class.java)

    @Singleton
    @Provides
    fun provideMoviesApi(retrofit: Retrofit): MoviesApi =
        retrofit.create(MoviesApi::class.java)

    @Singleton
    @Provides
    fun provideTrendingApi(retrofit: Retrofit): TrendingApi =
        retrofit.create(TrendingApi::class.java)

    @Singleton
    @Provides
    fun provideTvApi(retrofit: Retrofit): TvShowsApi =
        retrofit.create(TvShowsApi::class.java)

    @Singleton
    @Provides
    fun provideGenresApi(retrofit: Retrofit): GenresApi =
        retrofit.create(GenresApi::class.java)

    @Singleton
    @Provides
    fun provideDiscoverApi(retrofit: Retrofit): DiscoverApi =
        retrofit.create(DiscoverApi::class.java)

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
    fun provideConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
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

    @Singleton
    @Provides
    fun provideApiKeyInterceptor(): ApiKeyInterceptor = ApiKeyInterceptor()

    @Singleton
    @Provides
    fun provideErrorInterceptor(gson: Gson): ErrorInterceptor = ErrorInterceptor(gson)

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(logger: HttpLoggingInterceptor.Logger): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(logger).setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideHttpLoggerInterceptor(): HttpLoggingInterceptor.Logger = HttpLoggerInterceptor()
}

