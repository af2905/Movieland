package com.github.af2905.movieland.core.data.mapper

import com.github.af2905.movieland.core.common.model.item.PersonDetailItem
import com.github.af2905.movieland.core.common.model.item.PersonMovieCreditsCastItem
import com.github.af2905.movieland.core.data.database.entity.PersonDetail
import com.github.af2905.movieland.core.data.database.entity.PersonMovieCreditsCast
import com.github.af2905.movieland.core.data.dto.people.PersonDetailDto
import com.github.af2905.movieland.core.data.dto.people.PersonMovieCreditsCastDto
import javax.inject.Inject

class PersonDetailMapper @Inject constructor(
    private val personMovieCreditsCastMapper: PersonMovieCreditsCastMapper
) {
    @JvmName(DTO_TO_UI_ITEM_MAPPER)
    fun map(input: PersonDetailDto): PersonDetailItem = with(input) {
        PersonDetailItem(
            id = id,
            name = name,
            birthday = birthday,
            knownForDepartment = knownForDepartment,
            deathDay = deathDay,
            gender = gender,
            popularity = popularity,
            biography = biography,
            placeOfBirth = placeOfBirth,
            adult = adult,
            homepage = homepage,
            profilePath = profilePath
        )
    }

    @JvmName(ENTITY_TO_UI_ITEM_MAPPER)
    fun map(input: PersonDetail): PersonDetailItem = with(input) {
        PersonDetailItem(
            id = id,
            name = name,
            birthday = birthday,
            knownForDepartment = knownForDepartment,
            deathDay = deathDay,
            gender = gender,
            popularity = popularity,
            biography = biography,
            placeOfBirth = placeOfBirth,
            adult = adult,
            homepage = homepage,
            profilePath = profilePath,
            liked = liked,
            personMovieCreditsCasts = personMovieCreditsCastMapper.map(personMovieCreditsCasts)
        )
    }

    @JvmName(UI_ITEM_TO_ENTITY_MAPPER)
    fun map(input: PersonDetailItem): PersonDetail = with(input) {
        PersonDetail(
            id = id,
            name = name,
            birthday = birthday,
            knownForDepartment = knownForDepartment,
            deathDay = deathDay,
            gender = gender,
            popularity = popularity,
            biography = biography,
            placeOfBirth = placeOfBirth,
            adult = adult,
            homepage = homepage,
            profilePath = profilePath,
            liked = liked,
            personMovieCreditsCasts = personMovieCreditsCastMapper.map(personMovieCreditsCasts)
        )
    }
}

class PersonMovieCreditsCastMapper @Inject constructor() {
    @JvmName(DTO_TO_UI_ITEM_MAPPER)
    fun map(input: List<PersonMovieCreditsCastDto>): List<PersonMovieCreditsCastItem> =
        input.map { dto -> map(dto) }

    @JvmName(ENTITY_TO_UI_ITEM_MAPPER)
    fun map(input: List<PersonMovieCreditsCast>): List<PersonMovieCreditsCastItem> =
        input.map { entity -> map(entity) }

    @JvmName(UI_ITEM_TO_ENTITY_MAPPER)
    fun map(input: List<PersonMovieCreditsCastItem>): List<PersonMovieCreditsCast> =
        input.map { item -> map(item) }

    private fun map(input: PersonMovieCreditsCastDto): PersonMovieCreditsCastItem = with(input) {
        PersonMovieCreditsCastItem(
            id = id,
            adult = adult,
            backdropPath = backdropPath,
            genreIds = genreIds,
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            releaseDate = releaseDate,
            title = title,
            video = video,
            voteAverage = voteAverage,
            voteCount = voteCount,
            character = character,
            creditId = creditId,
            order = order
        )
    }

    private fun map(input: PersonMovieCreditsCast): PersonMovieCreditsCastItem = with(input) {
        PersonMovieCreditsCastItem(
            id = id,
            adult = adult,
            backdropPath = backdropPath,
            genreIds = genreIds,
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            releaseDate = releaseDate,
            title = title,
            video = video,
            voteAverage = voteAverage,
            voteCount = voteCount,
            character = character,
            creditId = creditId,
            order = order
        )
    }

    private fun map(input: PersonMovieCreditsCastItem): PersonMovieCreditsCast = with(input) {
        PersonMovieCreditsCast(
            id = id,
            adult = adult,
            backdropPath = backdropPath,
            genreIds = genreIds,
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            releaseDate = releaseDate,
            title = title,
            video = video,
            voteAverage = voteAverage,
            voteCount = voteCount,
            character = character,
            creditId = creditId,
            order = order
        )
    }
}