package com.github.af2905.movieland.core.data.mapper

import com.github.af2905.movieland.core.common.mapper.ListMapperImpl
import com.github.af2905.movieland.core.common.mapper.Mapper
import com.github.af2905.movieland.core.common.model.item.MovieActorItem
import com.github.af2905.movieland.core.data.database.entity.plain.MovieActor
import com.github.af2905.movieland.core.data.dto.movie.MovieActorDto
import javax.inject.Inject

class MovieActorDtoToMovieActorListMapper @Inject constructor(
    mapper: MovieActorDtoToMovieActorMapper
) : ListMapperImpl<MovieActorDto, MovieActor>(mapper)

class MovieActorDtoToMovieActorMapper @Inject constructor() : Mapper<MovieActorDto, MovieActor> {
    override fun map(input: MovieActorDto): MovieActor = with(input) {
        MovieActor(
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

class MovieActorToMovieActorItemMapper @Inject constructor() : Mapper<MovieActor, MovieActorItem> {
    override fun map(input: MovieActor): MovieActorItem {
        return with(input) {
            MovieActorItem(
                id = id,
                adult = adult,
                gender = gender,
                knownForDepartment = knownForDepartment,
                name = name,
                originalName = originalName,
                popularity = popularity,
                profilePath = profilePath,
                castId = castId,
                character = character,
                creditId = creditId,
                order = order
            )
        }
    }
}

class MovieActorItemToMovieActorMapper @Inject constructor() : Mapper<MovieActorItem, MovieActor> {
    override fun map(input: MovieActorItem): MovieActor {
        return with(input) {
            MovieActor(
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
}