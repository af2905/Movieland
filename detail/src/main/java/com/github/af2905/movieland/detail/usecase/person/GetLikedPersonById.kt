package com.github.af2905.movieland.detail.usecase.person

import com.github.af2905.movieland.core.common.model.item.PersonDetailItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.PersonDetailMapper
import com.github.af2905.movieland.core.repository.PeopleRepository
import com.github.af2905.movieland.detail.usecase.params.GetLikedPersonDetailByIdParams
import javax.inject.Inject

class GetLikedPersonById @Inject constructor(
    private val peopleRepository: PeopleRepository,
    private val mapper: PersonDetailMapper
) : CoroutineUseCase<GetLikedPersonDetailByIdParams, PersonDetailItem?>() {

    override suspend fun execute(params: GetLikedPersonDetailByIdParams): PersonDetailItem? {
        return peopleRepository.getPersonDetailById(params.personId)?.let { mapper.map(it) }
    }
}