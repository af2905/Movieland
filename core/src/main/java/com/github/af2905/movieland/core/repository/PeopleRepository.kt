package com.github.af2905.movieland.core.repository

import com.github.af2905.movieland.core.data.PersonItem

interface PeopleRepository {
    suspend fun getPersonDetail(
        personId: Int,
        language: String?
    ): PersonItem
}