package com.github.af2905.movieland.core.repository

import com.github.af2905.movieland.core.data.dto.people.PersonDetailDto
import com.github.af2905.movieland.core.data.dto.people.PersonMovieCreditsCastDto

interface PeopleRepository {

    suspend fun getPersonDetail(personId: Int, language: String?): PersonDetailDto
    suspend fun getPersonMovieCredits(personId: Int, language: String?): List<PersonMovieCreditsCastDto>
}