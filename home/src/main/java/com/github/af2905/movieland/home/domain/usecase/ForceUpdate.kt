package com.github.af2905.movieland.home.domain.usecase

import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.home.repository.ForceUpdateRepository
import javax.inject.Inject

class ForceUpdate @Inject constructor(private val forceUpdateRepository: ForceUpdateRepository) :
    CoroutineUseCase<Unit, Unit>() {

    override suspend fun execute(params: Unit) = forceUpdateRepository.forceUpdate()
}