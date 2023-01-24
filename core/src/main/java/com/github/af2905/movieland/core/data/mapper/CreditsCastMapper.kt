package com.github.af2905.movieland.core.data.mapper

import com.github.af2905.movieland.core.common.model.item.CreditsCastItem
import com.github.af2905.movieland.core.data.database.entity.plain.CreditsCast
import com.github.af2905.movieland.core.data.dto.CreditsCastDto
import javax.inject.Inject

class CreditsCastMapper @Inject constructor() {
    @JvmName(DTO_TO_UI_ITEM_MAPPER)
    fun map(input: List<CreditsCastDto>): List<CreditsCastItem> =
        input.map { dto -> map(dto) }

    @JvmName(ENTITY_TO_UI_ITEM_MAPPER)
    fun map(input: List<CreditsCast>): List<CreditsCastItem> =
        input.map { entity -> map(entity) }

    @JvmName(UI_ITEM_TO_ENTITY_MAPPER)
    fun map(input: List<CreditsCastItem>): List<CreditsCast> =
        input.map { entity -> map(entity) }

    private fun map(input: CreditsCastDto): CreditsCastItem = with(input) {
        CreditsCastItem(
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

    private fun map(input: CreditsCast): CreditsCastItem = with(input) {
        CreditsCastItem(
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

    private fun map(input: CreditsCastItem): CreditsCast = with(input) {
        CreditsCast(
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