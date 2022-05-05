package com.github.af2905.movieland.data.mapper

import com.github.af2905.movieland.data.dto.movie.MovieActorDto
import com.github.af2905.movieland.helper.mapper.IMapper
import com.github.af2905.movieland.presentation.model.item.MovieActorItem
import javax.inject.Inject

class MovieActorDtoMapper @Inject constructor() : IMapper<MovieActorDto, MovieActorItem> {
    override fun map(input: MovieActorDto): MovieActorItem {
        return MovieActorItem(
            id = input.id,
            adult = input.adult,
            gender = input.gender,
            knownForDepartment = input.knownForDepartment,
            name = input.name,
            originalName = input.originalName,
            popularity = input.popularity,
            castId = input.castId,
            character = input.character,
            creditId = input.creditId,
            order = input.order
        ).also { it.profilePath = input.profilePath }
    }
}
