package com.github.af2905.movieland.core.repository

import com.github.af2905.movieland.core.data.database.entity.Person
import com.github.af2905.movieland.core.data.database.entity.PersonDetail
import com.github.af2905.movieland.core.data.dto.people.PersonDetailDto
import com.github.af2905.movieland.core.data.dto.people.PersonMovieCreditsCastDto
import kotlinx.coroutines.flow.Flow

interface PeopleRepository {

    suspend fun getPopularPeople(language: String?): Flow<List<Person>>
}