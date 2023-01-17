package com.github.af2905.movieland.detail.usecase.person

import com.github.af2905.movieland.core.common.model.item.PersonDetailItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.PersonDetailMapper
import com.github.af2905.movieland.core.repository.PeopleRepository
import com.github.af2905.movieland.detail.usecase.params.PersonDetailParams
import javax.inject.Inject

class GetPersonDetail @Inject constructor(
    private val peopleRepository: PeopleRepository,
    private val mapper: PersonDetailMapper
) : CoroutineUseCase<PersonDetailParams, PersonDetailItem>() {
    override suspend fun execute(params: PersonDetailParams): PersonDetailItem {
        return mapper.map(
            peopleRepository.getPersonDetail(
                personId = params.personId,
                language = params.language
            )
        )
    }
}