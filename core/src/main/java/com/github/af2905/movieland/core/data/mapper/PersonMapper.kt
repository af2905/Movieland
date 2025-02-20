package com.github.af2905.movieland.core.data.mapper

import com.github.af2905.movieland.core.data.database.entity.KnownFor
import com.github.af2905.movieland.core.data.database.entity.MediaType
import com.github.af2905.movieland.core.data.database.entity.Person
import com.github.af2905.movieland.core.data.dto.people.KnownForDto
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

class KnownForMapper @Inject constructor() {

    fun map(input: List<KnownForDto>): List<KnownFor> = input.map { dto -> map(dto) }

    private fun map(input: KnownForDto): KnownFor = with(input) {
        KnownFor(
            id = id,
            name = name,
            title = title,
            backdropPath = backdropPath,
            firstAirDate = firstAirDate,
            genreIds = genreIds,
            releaseDate = releaseDate,
            mediaType = MediaType.fromString(mediaType) ?: MediaType.MOVIE,
            originCountry = originCountry,
            originalLanguage = originalLanguage,
            originalName = originalName,
            overview = overview,
            posterPath = posterPath,
            voteAverage = voteAverage,
            voteCount = voteCount
        )
    }
}