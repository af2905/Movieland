package com.github.af2905.movieland.core.data.api

import com.github.af2905.movieland.core.data.dto.credits.CreditsDto
import com.github.af2905.movieland.core.data.dto.movie.ExternalIds
import com.github.af2905.movieland.core.data.dto.movie.VideosDto
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

    @GET("tv/{tv_id}")
    suspend fun getTvShowDetail(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String? = null
    ): TvShowDetailDto

    @GET("tv/{tv_id}/similar")
    suspend fun getSimilarTvShows(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): TvShowsDto

    @GET("tv/{tv_id}/recommendations")
    suspend fun getRecommendedTvShows(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): TvShowsDto

    @GET("tv/{tv_id}/credits")
    suspend fun getTvShowCredits(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String? = null
    ): CreditsDto

    @GET("tv/{tv_id}/aggregate_credits")
    suspend fun getTvShowAggregateCredits(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String? = null
    ): CreditsDto

    @GET("tv/{tv_id}/videos")
    suspend fun getTvShowVideos(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String? = null
    ): VideosDto

    @GET("tv/{tv_id}/external_ids")
    suspend fun getTvShowExternalIds(
        @Path("tv_id") tvId: Int
    ): ExternalIds
}