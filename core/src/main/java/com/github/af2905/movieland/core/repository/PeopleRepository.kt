package com.github.af2905.movieland.core.repository

import com.github.af2905.movieland.core.data.database.entity.Person
import com.github.af2905.movieland.core.data.database.entity.PersonDetail
import com.github.af2905.movieland.core.data.dto.people.PersonDetailDto
import com.github.af2905.movieland.core.data.dto.people.PersonMovieCreditsCastDto

interface PeopleRepository {

    suspend fun getPersonDetail(personId: Int, language: String?): PersonDetailDto
    suspend fun getPersonMovieCredits(
        personId: Int,
        language: String?
    ): List<PersonMovieCreditsCastDto>

    suspend fun getPopularPeople(language: String?, page: Int?, forceUpdate: Boolean): List<Person>

    suspend fun savePersonDetail(personDetail: PersonDetail): Boolean
    suspend fun removePersonDetail(personDetail: PersonDetail): Boolean
    suspend fun getPersonDetailById(id: Int): PersonDetail?
    suspend fun getAllSavedPersonDetail(): List<PersonDetail>

    suspend fun getCachedPopularPeople(): List<Person>
}