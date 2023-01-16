package com.github.af2905.movieland.profile.usecase

import com.github.af2905.movieland.core.common.model.item.PersonDetailItem
import com.github.af2905.movieland.core.common.model.item.PersonDetailItem.Companion.mapToPersonItem
import com.github.af2905.movieland.core.common.model.item.PersonItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.PersonDetailMapper
import com.github.af2905.movieland.core.repository.PeopleRepository
import javax.inject.Inject

class GetAllSavedPeople @Inject constructor(
    private val peopleRepository: PeopleRepository,
    private val mapper: PersonDetailMapper
) : CoroutineUseCase<Unit, List<PersonItem>>() {

    override suspend fun execute(params: Unit): List<PersonItem> {
        val response = peopleRepository.getAllSavedPersonDetail()
        val list: List<PersonDetailItem> = response.map { personDetail -> mapper.map(personDetail) }
        return list.map { detailItem -> detailItem.mapToPersonItem() }
    }
}