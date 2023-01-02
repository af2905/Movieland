package com.github.af2905.movieland.core.data.api

import com.github.af2905.movieland.core.data.ApiParams
import com.github.af2905.movieland.core.data.ApiParams.TV_ID
import com.github.af2905.movieland.core.data.dto.tv.TvShowDetailDto
import com.github.af2905.movieland.core.data.dto.tv.TvShowsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowsApi {

    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query(ApiParams.LANGUAGE) language: String? = null,
        @Query(ApiParams.PAGE) page: Int? = null
    ): TvShowsDto

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShows(
        @Query(ApiParams.LANGUAGE) language: String? = null,
        @Query(ApiParams.PAGE) page: Int? = null
    ): TvShowsDto

    @GET("tv/{$TV_ID}")
    suspend fun getTvShowDetail(
        @Path(TV_ID) tvId: Int,
        @Query(ApiParams.LANGUAGE) language: String? = null
    ): TvShowDetailDto

    @GET("tv/{$TV_ID}/similar")
    suspend fun getSimilarTvShows(
        @Path(ApiParams.TV_ID) tvId: Int,
        @Query(ApiParams.LANGUAGE) language: String? = null
    ): TvShowsDto
}