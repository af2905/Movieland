package com.github.af2905.movieland.core.repository

import com.github.af2905.movieland.core.data.database.entity.PersonDetail

interface PeopleRepository {

    suspend fun getPersonDetail(personId: Int, language: String?): PersonDetail
}