package com.github.af2905.movieland.domain.usecase.people

import com.github.af2905.movieland.domain.repository.PeopleRepository
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.domain.usecase.params.PersonDetailParams
import com.github.af2905.movieland.presentation.model.item.PersonItem
import javax.inject.Inject

class GetPersonDetail @Inject constructor(
    private val peopleRepository: PeopleRepository
) : CoroutineUseCase<PersonDetailParams, PersonItem>() {
    override suspend fun execute(params: PersonDetailParams): PersonItem {
        return peopleRepository.getPersonDetail(params.movieId, params.language)
    }
}