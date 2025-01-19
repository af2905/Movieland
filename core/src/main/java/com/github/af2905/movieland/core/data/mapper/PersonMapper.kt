package com.github.af2905.movieland.core.data.mapper

import com.github.af2905.movieland.core.data.database.entity.Person
import com.github.af2905.movieland.core.data.dto.people.PersonDto
import javax.inject.Inject

class PersonMapper @Inject constructor(
    private val knownForMapper: KnownForMapper
) {
    fun map(dto: PersonDto): Person {
        return Person(
            id = dto.id,
            name = dto.name,
            profilePath = dto.profilePath,
            knownFor = dto.knownFor?.let { knownForMapper.map(it) },
            popularity = dto.popularity
        )
    }

    fun map(input: List<PersonDto>): List<Person> {
        return input.map { map(it) }
    }
}