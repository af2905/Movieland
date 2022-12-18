package com.github.af2905.movieland.core.data.mapper

import com.github.af2905.movieland.core.common.model.item.MovieCreditsCastItem
import com.github.af2905.movieland.core.data.database.entity.plain.MovieCreditsCast
import com.github.af2905.movieland.core.data.dto.movie.MovieCreditsCastDto
import javax.inject.Inject

class MovieCreditsCastMapper @Inject constructor() {
    @JvmName(DTO_TO_UI_ITEM_MAPPER)
    fun map(input: List<MovieCreditsCastDto>): List<MovieCreditsCastItem> =
        input.map { dto -> map(dto) }

    @JvmName(ENTITY_TO_UI_ITEM_MAPPER)
    fun map(input: List<MovieCreditsCast>): List<MovieCreditsCastItem> =
        input.map { entity -> map(entity) }

    @JvmName(UI_ITEM_TO_ENTITY_MAPPER)
    fun map(input: List<MovieCreditsCastItem>): List<MovieCreditsCast> =
        input.map { entity -> map(entity) }

    private fun map(input: MovieCreditsCastDto): MovieCreditsCastItem = with(input) {
        MovieCreditsCastItem(
            id = id,
            adult = adult,
            gender = gender,
            knownForDepartment = knownForDepartment,
            name = name,
            originalName = originalName,
            popularity = popularity,
            castId = castId,
            character = character,
            creditId = creditId,
            order = order,
            profilePath = profilePath
        )
    }

    private fun map(input: MovieCreditsCast): MovieCreditsCastItem = with(input) {
        MovieCreditsCastItem(
            id = id,
            adult = adult,
            gender = gender,
            knownForDepartment = knownForDepartment,
            name = name,
            originalName = originalName,
            popularity = popularity,
            castId = castId,
            character = character,
            creditId = creditId,
            order = order,
            profilePath = profilePath
        )
    }

    private fun map(input: MovieCreditsCastItem): MovieCreditsCast = with(input) {
        MovieCreditsCast(
            id = id,
            adult = adult,
            gender = gender,
            knownForDepartment = knownForDepartment,
            name = name,
            originalName = originalName,
            popularity = popularity,
            castId = castId,
            character = character,
            creditId = creditId,
            order = order,
            profilePath = profilePath
        )
    }
}