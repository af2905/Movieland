package com.github.af2905.movieland.core.repository.impl

import com.github.af2905.movieland.core.data.api.PeopleApi
import com.github.af2905.movieland.core.data.database.dao.PersonDao
import com.github.af2905.movieland.core.data.database.entity.Person
import com.github.af2905.movieland.core.data.database.entity.PersonType
import com.github.af2905.movieland.core.data.mapper.PersonMapper
import com.github.af2905.movieland.core.repository.PeopleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PeopleRepositoryImpl @Inject constructor(
    private val personDao: PersonDao,
    private val peopleApi: PeopleApi,
    private val mapper: PersonMapper,
) : PeopleRepository {

    override suspend fun getPopularPeople(language: String?): Flow<List<Person>> = flow {

        val cachedPeople = personDao.getPeopleByType(PersonType.POPULAR).firstOrNull()
        val lastUpdated = cachedPeople?.firstOrNull()?.timeStamp ?: 0L
        val isCacheStale = System.currentTimeMillis() - lastUpdated > TimeUnit.HOURS.toMillis(8)

        if (cachedPeople.isNullOrEmpty() || isCacheStale) {
            try {
                val response = peopleApi.getPersonPopular(
                    language = language
                )
                val people = mapper.map(response.results).map { person ->
                    person.copy(
                        personType = PersonType.POPULAR,
                        timeStamp = System.currentTimeMillis()
                    )
                }.filter { person -> !person.profilePath.isNullOrEmpty() }

                if (people.isNotEmpty()) {
                    personDao.deletePeopleByType(PersonType.POPULAR)
                    personDao.insertPeople(people)
                }
            } catch (e: Exception) {
                // Handle API errors (e.g., log or fallback)
            }
        }
        emitAll(personDao.getPeopleByType(PersonType.POPULAR))
    }.catch { emit(emptyList()) }
}