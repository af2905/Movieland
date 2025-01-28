package com.github.af2905.movieland.core.data.mapper

import com.github.af2905.movieland.core.data.database.entity.CreditsCast
import com.github.af2905.movieland.core.data.dto.CreditsCastDto
import javax.inject.Inject

class CreditsCastMapper @Inject constructor() {
    fun map(dto: CreditsCastDto, movieId: Int?): CreditsCast =
        CreditsCast(
            id = dto.id,
            movieId = movieId,
            adult = dto.adult,
            gender = dto.gender,
            knownForDepartment = dto.knownForDepartment,
            name = dto.name,
            originalName = dto.originalName,
            popularity = dto.popularity,
            profilePath = dto.profilePath,
            castId = dto.castId,
            character = dto.character,
            creditId = dto.creditId,
            order = dto.order
        )
}
