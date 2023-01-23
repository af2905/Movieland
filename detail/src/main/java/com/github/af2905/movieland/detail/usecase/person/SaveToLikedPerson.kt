package com.github.af2905.movieland.detail.usecase.person

import com.github.af2905.movieland.core.common.model.item.PersonDetailItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.PersonDetailMapper
import com.github.af2905.movieland.core.repository.PeopleRepository
import javax.inject.Inject

class SaveToLikedPerson @Inject constructor(
    private val peopleRepository: PeopleRepository,
    private val mapper: PersonDetailMapper
) : CoroutineUseCase<LikedPersonDetailParams, Boolean>() {

    override suspend fun execute(params: LikedPersonDetailParams): Boolean {
        return peopleRepository.savePersonDetail(personDetail = mapper.map(params.personDetail))
    }
}

data class LikedPersonDetailParams(val personDetail: PersonDetailItem)