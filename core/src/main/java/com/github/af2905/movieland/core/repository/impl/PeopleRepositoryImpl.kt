package com.github.af2905.movieland.core.repository.impl

import com.github.af2905.movieland.core.data.api.PeopleApi
import com.github.af2905.movieland.core.data.database.dao.PersonDao
import com.github.af2905.movieland.core.data.database.dao.PersonDetailDao
import com.github.af2905.movieland.core.data.database.entity.Person
import com.github.af2905.movieland.core.data.database.entity.PersonDetail
import com.github.af2905.movieland.core.data.datastore.ResourceDatastore
import com.github.af2905.movieland.core.data.dto.people.PersonDetailDto
import com.github.af2905.movieland.core.data.dto.people.PersonMovieCreditsCastDto
import com.github.af2905.movieland.core.data.mapper.PersonMapper
import com.github.af2905.movieland.core.repository.PeopleRepository
import com.github.af2905.movieland.util.extension.isNullOrEmpty
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val DEFAULT_UPDATE_PEOPLE_HOURS = 4L

class PeopleRepositoryImpl @Inject constructor(
    private val peopleApi: PeopleApi,
    private val personDao: PersonDao,
    private val personDetailDao: PersonDetailDao,
    private val personMapper: PersonMapper,
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

    override suspend fun getPopularPeople(
        language: String?,
        page: Int?,
        forceUpdate: Boolean
    ): List<Person> {

        val count = personDao.getCount()

        val timeStamp = count?.let { personDao.getTimeStamp() }

        val currentTime = Calendar.getInstance().timeInMillis

        val timeDiff = timeStamp?.let {
            periodOfTimeInHours(
                timeStamp = it,
                currentTime = currentTime
            )
        }

        val needToUpdate = timeDiff?.let {
            it > TimeUnit.HOURS.toMillis(DEFAULT_UPDATE_PEOPLE_HOURS)
        }

        if (count.isNullOrEmpty() || needToUpdate == true || forceUpdate) {
            val dto = peopleApi.getPersonPopular(
                language = language ?: resourceDatastore.getLanguage(),
                page = page
            )
            personMapper.map(dto.results, currentTime).forEach { personDao.save(it) }
        }
        return personDao.get().orEmpty()
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

    override suspend fun getCachedPopularPeople(): List<Person> {
        return personDao.get().orEmpty()
    }

    private fun periodOfTimeInHours(timeStamp: Long, currentTime: Long) =
        TimeUnit.MILLISECONDS.toHours(currentTime - timeStamp)
}