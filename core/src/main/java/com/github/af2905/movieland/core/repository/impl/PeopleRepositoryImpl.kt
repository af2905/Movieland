package com.github.af2905.movieland.core.repository.impl

import com.github.af2905.movieland.core.data.api.PeopleApi
import com.github.af2905.movieland.core.data.database.entity.PersonDetail
import com.github.af2905.movieland.core.data.datastore.ResourceDatastore
import com.github.af2905.movieland.core.data.mapper.PersonDetailDtoToPersonDetailMapper
import com.github.af2905.movieland.core.repository.PeopleRepository
import javax.inject.Inject

class PeopleRepositoryImpl @Inject constructor(
    private val peopleApi: PeopleApi,
    private val mapper: PersonDetailDtoToPersonDetailMapper,
    private val resourceDatastore: ResourceDatastore
) : PeopleRepository {

    override suspend fun getPersonDetail(
        personId: Int,
        language: String?
    ): PersonDetail {
        val response = peopleApi.getPersonDetail(
            personId = personId,
            language = language ?: resourceDatastore.getLanguage()
        )
        return mapper.map(response)
    }
}