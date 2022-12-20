package com.github.af2905.movieland.core.repository.impl

import com.github.af2905.movieland.core.data.api.PeopleApi
import com.github.af2905.movieland.core.data.database.dao.PersonDetailDao
import com.github.af2905.movieland.core.data.database.entity.PersonDetail
import com.github.af2905.movieland.core.data.datastore.ResourceDatastore
import com.github.af2905.movieland.core.data.dto.people.PersonDetailDto
import com.github.af2905.movieland.core.data.dto.people.PersonMovieCreditsCastDto
import com.github.af2905.movieland.core.repository.PeopleRepository
import javax.inject.Inject

class PeopleRepositoryImpl @Inject constructor(
    private val peopleApi: PeopleApi,
    private val personDetailDao: PersonDetailDao,
    private val resourceDatastore: ResourceDatastore
) : PeopleRepository {

    override suspend fun getPersonDetail(
        personId: Int,
        language: String?
    ): PersonDetailDto {
        return peopleApi.getPersonDetail(
            personId = personId,
            language = language ?: resourceDatastore.getLanguage()
        )
    }

    override suspend fun getPersonMovieCredits(
        personId: Int,
        language: String?
    ): List<PersonMovieCreditsCastDto> {
        return peopleApi.getPersonMovieCredits(
            personId = personId,
            language = language ?: resourceDatastore.getLanguage()
        ).cast.orEmpty()
    }

    override suspend fun savePersonDetail(personDetail: PersonDetail): Boolean {
        return personDetailDao.save(personDetail)?.let { true } ?: false
    }

    override suspend fun removePersonDetail(personDetail: PersonDetail): Boolean {
        return personDetailDao.delete(personDetail)?.let { true } ?: false
    }

    override suspend fun getPersonDetailById(id: Int): PersonDetail? {
        return personDetailDao.getById(id)
    }

    override suspend fun getAllSavedPersonDetail(): List<PersonDetail> {
        return personDetailDao.getAll() ?: emptyList()
    }
}