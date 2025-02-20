package com.github.af2905.movieland.core.repository

import com.github.af2905.movieland.core.common.network.ResultWrapper
import com.github.af2905.movieland.core.data.database.entity.Person
import com.github.af2905.movieland.core.data.database.entity.PersonCreditsCast
import com.github.af2905.movieland.core.data.database.entity.PersonDetail
import kotlinx.coroutines.flow.Flow

interface PeopleRepository {
    suspend fun getPopularPeople(language: String?): Flow<List<Person>>
    suspend fun getPersonDetails(
        personId: Int,
        language: String?
    ): Flow<ResultWrapper<PersonDetail>>

    suspend fun getPersonCredits(
        personId: Int,
        language: String?
    ): Flow<ResultWrapper<List<PersonCreditsCast>>>
}