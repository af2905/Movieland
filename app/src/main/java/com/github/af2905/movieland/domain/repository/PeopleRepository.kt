package com.github.af2905.movieland.domain.repository

import com.github.af2905.movieland.presentation.model.item.PersonItem

interface PeopleRepository {
    suspend fun getPersonDetail(
        personId: Int,
        language: String?
    ): PersonItem
}