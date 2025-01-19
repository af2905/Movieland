package com.github.af2905.movieland.core.data.api

import com.github.af2905.movieland.core.data.ApiParams.TV_ID
import com.github.af2905.movieland.core.data.dto.CreditsDto
import com.github.af2905.movieland.core.data.dto.tv.TvShowDetailDto
import com.github.af2905.movieland.core.data.dto.tv.TvShowsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowsApi {

    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): TvShowsDto

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShows(
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): TvShowsDto

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTvShows(
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): TvShowsDto

    @GET("tv/airing_today")
    suspend fun getAiringTodayTvShows(
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): TvShowsDto

    @GET("tv/{$TV_ID}") //TODO check
    suspend fun getTvShowDetail(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String? = null
    ): TvShowDetailDto

    @GET("tv/{$TV_ID}/similar")
    suspend fun getSimilarTvShows(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String? = null
    ): TvShowsDto

    @GET("tv/{$TV_ID}/credits")
    suspend fun getTvShowCredits(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String? = null
    ): CreditsDto
}