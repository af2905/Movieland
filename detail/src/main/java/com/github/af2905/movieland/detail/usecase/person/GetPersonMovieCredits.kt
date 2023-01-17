package com.github.af2905.movieland.detail.usecase.person

import com.github.af2905.movieland.core.common.model.item.PersonMovieCreditsCastItem
import com.github.af2905.movieland.core.common.usecase.CoroutineUseCase
import com.github.af2905.movieland.core.data.mapper.PersonMovieCreditsCastMapper
import com.github.af2905.movieland.core.repository.PeopleRepository
import com.github.af2905.movieland.detail.usecase.params.PersonMovieCreditsParams
import javax.inject.Inject

class GetPersonMovieCredits @Inject constructor(
    private val peopleRepository: PeopleRepository,
    private val mapper: PersonMovieCreditsCastMapper
) : CoroutineUseCase<PersonMovieCreditsParams, List<PersonMovieCreditsCastItem>>() {
    override suspend fun execute(params: PersonMovieCreditsParams): List<PersonMovieCreditsCastItem> {
        val response = peopleRepository.getPersonMovieCredits(params.personId, params.language)
        return mapper.map(response).filterNot {
            it.releaseDate.isNullOrEmpty()
                    || it.character.isNullOrEmpty()
                    || (it.posterPath.isNullOrEmpty() && it.backdropPath.isNullOrEmpty())
        }
            .sortedByDescending { it.releaseDate }
    }
}