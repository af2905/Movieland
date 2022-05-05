package com.github.af2905.movieland.data.mapper

import com.github.af2905.movieland.data.dto.people.PersonDetailResponseDto
import com.github.af2905.movieland.helper.mapper.IMapper
import com.github.af2905.movieland.presentation.model.item.PersonItem
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