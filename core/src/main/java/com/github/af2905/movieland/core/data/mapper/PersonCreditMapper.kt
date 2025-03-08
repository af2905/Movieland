package com.github.af2905.movieland.core.data.mapper

import com.github.af2905.movieland.core.data.database.entity.MediaType
import com.github.af2905.movieland.core.data.database.entity.PersonCreditsCast
import com.github.af2905.movieland.core.data.dto.people.PersonCreditsCastDto
import javax.inject.Inject

class PersonCreditMapper @Inject constructor() {
    fun map(dto: PersonCreditsCastDto): PersonCreditsCast {
        return PersonCreditsCast(
            id = dto.id,
            adult = dto.adult,
            backdropPath = dto.backdropPath,
            genreIds = dto.genreIds,
            originalLanguage = dto.originalLanguage,
            originalTitle = dto.originalTitle,
            overview = dto.overview,
            popularity = dto.popularity,
            posterPath = dto.posterPath,
            releaseDate = dto.releaseDate,
            title = dto.title ?: dto.name,
            video = dto.video,
            voteAverage = dto.voteAverage,
            voteCount = dto.voteCount,
            character = dto.character,
            creditId = dto.creditId,
            order = dto.order,
            mediaType = MediaType.fromString(dto.mediaType) ?: MediaType.MOVIE
        )
    }
}