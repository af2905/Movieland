package com.github.af2905.movieland.core.data.api

import com.github.af2905.movieland.core.data.ApiParams
import com.github.af2905.movieland.core.data.ApiParams.LANGUAGE
import com.github.af2905.movieland.core.data.ApiParams.PERSON_ID
import com.github.af2905.movieland.core.data.dto.people.PersonDetailDto
import com.github.af2905.movieland.core.data.dto.people.PersonMovieCreditsDto
import com.github.af2905.movieland.core.data.dto.people.PersonPopularDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PeopleApi {

    @GET("person/{$PERSON_ID}")
    suspend fun getPersonDetail(
        @Path(PERSON_ID) personId: Int,
        @Query(LANGUAGE) language: String? = null
    ): PersonDetailDto

    @GET("person/popular")
    suspend fun getPersonPopular(
        @Query(LANGUAGE) language: String? = null,
        @Query(ApiParams.PAGE) page: Int? = null,
    ): PersonPopularDto

    @GET("person/{$PERSON_ID}/movie_credits")
    suspend fun getPersonMovieCredits(
        @Path(PERSON_ID) personId: Int,
        @Query(LANGUAGE) language: String? = null
    ): PersonMovieCreditsDto
}