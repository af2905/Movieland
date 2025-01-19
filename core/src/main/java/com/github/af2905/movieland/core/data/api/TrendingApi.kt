package com.github.af2905.movieland.core.data.api

import com.github.af2905.movieland.core.data.dto.movie.MoviesDto
import com.github.af2905.movieland.core.data.dto.people.PeopleDto
import com.github.af2905.movieland.core.data.dto.tv.TvShowsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TrendingApi {

    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") timeWindow: String,
        @Query("language") language: String? = null
    ): MoviesDto

    @GET("trending/tv/{time_window}")
    suspend fun getTrendingTvShows(
        @Path("time_window") timeWindow: String,
        @Query("language") language: String? = null
    ): TvShowsDto

    @GET("trending/person/{time_window}")
    suspend fun getTrendingPeople(
        @Path("time_window") timeWindow: String,
        @Query("language") language: String? = null
    ): PeopleDto
}