package com.github.af2905.movieland.domain.usecase.people

import com.github.af2905.movieland.domain.repository.PeopleRepository
import com.github.af2905.movieland.domain.usecase.CoroutineUseCase
import com.github.af2905.movieland.domain.usecase.params.PersonParams
import com.github.af2905.movieland.presentation.model.item.PersonItem
import javax.inject.Inject

class GetPersonDetails @Inject constructor(
    private val peopleRepository: PeopleRepository
) : CoroutineUseCase<PersonParams, PersonItem>() {
    override suspend fun execute(params: PersonParams): PersonItem {
        return peopleRepository.getPersonDetails(params.movieId, params.language)
    }
}