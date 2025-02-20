package com.github.af2905.movieland.core.repository.impl

import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.helper.StringProvider
import com.github.af2905.movieland.core.common.network.ResultWrapper
import com.github.af2905.movieland.core.data.api.PeopleApi
import com.github.af2905.movieland.core.data.database.dao.PersonDao
import com.github.af2905.movieland.core.data.database.entity.Person
import com.github.af2905.movieland.core.data.database.entity.PersonCreditsCast
import com.github.af2905.movieland.core.data.database.entity.PersonDetail
import com.github.af2905.movieland.core.data.database.entity.PersonType
import com.github.af2905.movieland.core.data.mapper.PersonCreditMapper
import com.github.af2905.movieland.core.data.mapper.PersonDetailMapper
import com.github.af2905.movieland.core.data.mapper.PersonMapper
import com.github.af2905.movieland.core.repository.PeopleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PeopleRepositoryImpl @Inject constructor(
    private val personDao: PersonDao,
    private val peopleApi: PeopleApi,
    private val personMapper: PersonMapper,
    private val personDetailMapper: PersonDetailMapper,
    private val personCreditMapper: PersonCreditMapper,
    private val stringProvider: StringProvider
) : PeopleRepository {

    override suspend fun getPopularPeople(language: String?): Flow<List<Person>> = flow {
        val cachedPeople = personDao.getPeopleByType(PersonType.POPULAR).firstOrNull()
        val lastUpdated = cachedPeople?.firstOrNull()?.timeStamp ?: 0L
        val isCacheStale = System.currentTimeMillis() - lastUpdated > TimeUnit.HOURS.toMillis(4)

        if (cachedPeople.isNullOrEmpty() || isCacheStale) {
            try {
                val response = peopleApi.getPersonPopular(language)
                val people = personMapper.map(response.results)
                    .map { person ->
                        person.copy(personType = PersonType.POPULAR, timeStamp = System.currentTimeMillis())
                    }
                    .filter { person -> !person.profilePath.isNullOrEmpty() }

                if (people.isNotEmpty()) {
                    personDao.deletePeopleByType(PersonType.POPULAR)
                    personDao.insertPeople(people)
                }
            } catch (e: Exception) {
                // Handle API errors (log, analytics, fallback)
            }
        }
        emitAll(personDao.getPeopleByType(PersonType.POPULAR))
    }.catch { emit(emptyList()) }

    override suspend fun getPersonDetails(personId: Int, language: String?): Flow<ResultWrapper<PersonDetail>> = flow {
        emit(ResultWrapper.Loading)
        try {
            val response = peopleApi.getPersonDetail(personId, language)
            val personDetail = personDetailMapper.map(response)
            emit(ResultWrapper.Success(personDetail))
        } catch (e: IOException) {
            emit(ResultWrapper.Error(stringProvider.getString(R.string.error_network), e))
        } catch (e: HttpException) {
            emit(ResultWrapper.Error(stringProvider.getString(R.string.error_server, e.code(), e.message()), e))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(stringProvider.getString(R.string.error_unexpected), e))
        }
    }

    override suspend fun getPersonCredits(personId: Int, language: String?): Flow<ResultWrapper<List<PersonCreditsCast>>> = flow {
        emit(ResultWrapper.Loading)
        try {
            val response = peopleApi.getPersonCredits(personId, language)
            val credits = response.cast?.map { personCreditMapper.map(it) }
                ?.filter { credit -> !credit.posterPath.isNullOrEmpty() }.orEmpty()
            emit(ResultWrapper.Success(credits))
        } catch (e: IOException) {
            emit(ResultWrapper.Error(stringProvider.getString(R.string.error_network), e))
        } catch (e: HttpException) {
            emit(ResultWrapper.Error(stringProvider.getString(R.string.error_server, e.code(), e.message()), e))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(stringProvider.getString(R.string.error_unexpected), e))
        }
    }
}
