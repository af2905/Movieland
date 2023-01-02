package com.github.af2905.movieland.core.data.mapper

import com.github.af2905.movieland.core.common.model.item.PersonItem
import com.github.af2905.movieland.core.data.database.entity.Person
import com.github.af2905.movieland.core.data.dto.people.PersonDto
import javax.inject.Inject

class PersonMapper @Inject constructor(
    private val knownForMapper: KnownForMapper
) {

    fun map(input: List<Person>): List<PersonItem> = input.map { map(it) }

    fun map(input: List<PersonDto>, timeStamp: Long): List<Person> =
        input.map { map(it, timeStamp) }

    private fun map(input: PersonDto, timeStamp: Long): Person = with(input) {
        Person(
            id = id,
            knownFor = knownFor?.let { knownForMapper.map(it) },
            name = name,
            profilePath = profilePath,
            popularity = popularity,
            timeStamp = timeStamp
        )
    }

    private fun map(input: Person): PersonItem = with(input) {
        PersonItem(
            id = id,
            personMovieCreditsCasts = knownFor?.let { knownForMapper.map(it) }.orEmpty(),
            name = name,
            profilePath = profilePath
        )
    }
}