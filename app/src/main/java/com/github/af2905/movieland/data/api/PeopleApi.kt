package com.github.af2905.movieland.data.api

import com.github.af2905.movieland.data.ApiParams.LANGUAGE
import com.github.af2905.movieland.data.ApiParams.PERSON_ID
import com.github.af2905.movieland.data.dto.people.PersonDetailResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PeopleApi {

    @GET("person/{$PERSON_ID}")
    suspend fun getPersonDetail(
        @Path(PERSON_ID) personId: Int,
        @Query(LANGUAGE) language: String? = null
    ): PersonDetailResponseDto
}