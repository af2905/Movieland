package com.github.af2905.movieland.data.mapper

import com.github.af2905.movieland.data.dto.people.PersonDetailsResponseDto
import com.github.af2905.movieland.helper.mapper.IMapper
import com.github.af2905.movieland.presentation.model.item.PersonItem
import javax.inject.Inject

class PersonDetailsDtoMapper @Inject constructor() : IMapper<PersonDetailsResponseDto, PersonItem> {
    override fun map(input: PersonDetailsResponseDto): PersonItem {
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
            imdbId = input.imdbId,
            homepage = input.homepage
        ).also { it.profilePath = input.profilePath }
    }
}