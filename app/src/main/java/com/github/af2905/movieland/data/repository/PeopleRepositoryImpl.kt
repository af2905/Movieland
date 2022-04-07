package com.github.af2905.movieland.data.repository

import com.github.af2905.movieland.data.api.PeopleApi
import com.github.af2905.movieland.data.datastore.ResourceDatastore
import com.github.af2905.movieland.data.mapper.PersonDetailsDtoMapper
import com.github.af2905.movieland.domain.repository.PeopleRepository
import com.github.af2905.movieland.presentation.model.item.PersonItem
import javax.inject.Inject

class PeopleRepositoryImpl @Inject constructor(
    private val peopleApi: PeopleApi,
    private val mapper: PersonDetailsDtoMapper,
    private val resourceDatastore: ResourceDatastore
) : PeopleRepository {

    override suspend fun getPersonDetails(
        personId: Int,
        language: String?
    ): PersonItem {
        val response = peopleApi.getPersonDetails(
            personId = personId,
            language = language ?: resourceDatastore.getLanguage()
        )
        return mapper.map(response)
    }
}