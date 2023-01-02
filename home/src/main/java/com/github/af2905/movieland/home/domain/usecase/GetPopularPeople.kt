package com.github.af2905.movieland.home.domain.usecase

import com.github.af2905.movieland.core.common.model.item.PersonItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.PersonMapper
import com.github.af2905.movieland.core.repository.PeopleRepository
import com.github.af2905.movieland.home.domain.params.PeopleParams
import javax.inject.Inject

class GetPopularPeople @Inject constructor(
    private val peopleRepository: PeopleRepository,
    private val mapper: PersonMapper
) : CoroutineUseCase<PeopleParams, List<PersonItem>>() {
    override suspend fun execute(params: PeopleParams): List<PersonItem> {
        val response = peopleRepository.getPopularPeople(
            language = params.language,
            page = params.page,
            forceUpdate = params.forceUpdate
        )
        return mapper.map(response).filterNot {
            it.profilePath.isNullOrEmpty()
        }
    }
}