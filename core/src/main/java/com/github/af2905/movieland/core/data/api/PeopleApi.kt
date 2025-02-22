package com.github.af2905.movieland.core.data.api

import com.github.af2905.movieland.core.data.dto.people.PeopleDto
import com.github.af2905.movieland.core.data.dto.people.PersonDetailDto
import com.github.af2905.movieland.core.data.dto.people.PersonCreditsDto
import com.github.af2905.movieland.core.data.dto.people.PersonExternalIds
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PeopleApi {

    @GET("person/{person_id}")
    suspend fun getPersonDetail(
        @Path("person_id") personId: Int,
        @Query("language") language: String? = null
    ): PersonDetailDto

    @GET("person/popular")
    suspend fun getPersonPopular(
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null,
    ): PeopleDto

    @GET("person/{person_id}/combined_credits")
    suspend fun getPersonCredits(
        @Path("person_id") personId: Int,
        @Query("language") language: String? = null
    ): PersonCreditsDto

        @GET("person/{person_id}/external_ids")
        suspend fun getPersonExternalIds(
            @Path("person_id") personId: Int
        ): PersonExternalIds
}