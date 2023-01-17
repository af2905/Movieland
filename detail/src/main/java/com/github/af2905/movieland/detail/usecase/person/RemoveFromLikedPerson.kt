package com.github.af2905.movieland.detail.usecase.person

import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.PersonDetailMapper
import com.github.af2905.movieland.core.repository.PeopleRepository
import com.github.af2905.movieland.detail.usecase.params.UnlikedPersonDetailParams
import javax.inject.Inject

class RemoveFromLikedPerson @Inject constructor(
    private val peopleRepository: PeopleRepository,
    private val mapper: PersonDetailMapper
) : CoroutineUseCase<UnlikedPersonDetailParams, Boolean>() {

    override suspend fun execute(params: UnlikedPersonDetailParams): Boolean {
        return peopleRepository.removePersonDetail(personDetail = mapper.map(params.personDetail))
    }
}