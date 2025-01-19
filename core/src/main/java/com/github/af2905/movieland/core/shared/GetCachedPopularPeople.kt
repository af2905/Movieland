package com.github.af2905.movieland.core.shared

import com.github.af2905.movieland.core.common.model.item.PersonItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.PersonMapper
import com.github.af2905.movieland.core.repository.PeopleRepository
import javax.inject.Inject

class GetCachedPopularPeople @Inject constructor(
    private val peopleRepository: PeopleRepository,
    private val mapper: PersonMapper
) : CoroutineUseCase<Unit, List<PersonItem>>() {
    override suspend fun execute(params: Unit): List<PersonItem> {
        //val people = peopleRepository.getCachedPopularPeople()
        return emptyList()
    }
}