package com.github.af2905.movieland.core.data.mapper

import com.github.af2905.movieland.core.common.mapper.IMapper
import com.github.af2905.movieland.core.data.PersonItem
import com.github.af2905.movieland.core.data.dto.people.PersonDetailResponseDto
import javax.inject.Inject

class PersonDetailsDtoMapper @Inject constructor() : IMapper<PersonDetailResponseDto, PersonItem> {
    override fun map(input: PersonDetailResponseDto): PersonItem {
        return PersonItem(
            id = input.id,
            name = input.name,
            birthday = input.birthday,
            knownForDepartment = input.knownForDepartment,
            deathday = input.deathday,
            alsoKnownAs = input.alsoKnownAs,
            gender = input.gender,
            popularity = input.popularity,
            biography = input.biography,
            placeOfBirth = input.placeOfBirth,
            adult = input.adult,
            homepage = input.homepage
        ).also { it.profilePath = input.profilePath }
    }
}