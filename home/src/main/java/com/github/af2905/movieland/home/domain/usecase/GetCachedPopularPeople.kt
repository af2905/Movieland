package com.github.af2905.movieland.home.domain.usecase

import com.github.af2905.movieland.core.common.model.item.PersonItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.PersonMapper
import com.github.af2905.movieland.core.repository.PeopleRepository
import com.github.af2905.movieland.home.domain.params.CachedPeopleParams
import javax.inject.Inject

class GetCachedPopularPeople @Inject constructor(
    private val peopleRepository: PeopleRepository,
    private val mapper: PersonMapper
) : CoroutineUseCase<CachedPeopleParams, List<PersonItem>>() {
    override suspend fun execute(params: CachedPeopleParams): List<PersonItem> {
        val people = peopleRepository.getCachedPopularPeople()
        return mapper.map(people)
    }
}