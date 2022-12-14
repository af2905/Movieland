package com.github.af2905.movieland.core.data.mapper

import com.github.af2905.movieland.core.common.mapper.Mapper
import com.github.af2905.movieland.core.common.model.item.PersonDetailItem
import com.github.af2905.movieland.core.data.database.entity.PersonDetail
import com.github.af2905.movieland.core.data.dto.people.PersonDetailDto
import javax.inject.Inject

class PersonDetailDtoToPersonDetailMapper @Inject constructor() :
    Mapper<PersonDetailDto, PersonDetail> {
    override fun map(input: PersonDetailDto): PersonDetail = with(input) {
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
            profilePath = profilePath
        )
    }
}

class PersonDetailToPersonDetailItemMapper @Inject constructor() :
    Mapper<PersonDetail, PersonDetailItem> {
    override fun map(input: PersonDetail): PersonDetailItem = with(input) {
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
}

class PersonDetailItemToPersonDetailMapper @Inject constructor() :
    Mapper<PersonDetailItem, PersonDetail> {
    override fun map(input: PersonDetailItem): PersonDetail = with(input) {
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
            profilePath = profilePath
        )
    }
}