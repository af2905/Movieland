package com.github.af2905.movieland.data.mapper

import com.github.af2905.movieland.data.dto.DatesDto
import com.github.af2905.movieland.data.dto.MovieDto
import com.github.af2905.movieland.data.dto.MoviesResponseDto
import com.github.af2905.movieland.data.entity.DatesEntity
import com.github.af2905.movieland.data.entity.MovieEntity
import com.github.af2905.movieland.data.entity.MoviesResponseEntity
import com.github.af2905.movieland.helper.mapper.IMapper
import com.github.af2905.movieland.helper.mapper.ListMapper
import javax.inject.Inject

class MoviesResponseDtoToEntityMapper @Inject constructor(
    private val datesMapper: DatesDtoToEntityMapper,
    private val movieMapper: MovieDtoToEntityListMapper
) : IMapper<MoviesResponseDto, MoviesResponseEntity> {
    override fun map(input: MoviesResponseDto) =
        with(input) {
            MoviesResponseEntity(
                dates = dates?.let { datesMapper.map(it) },
                page = page,
                movies = movieMapper.map(movies),
                totalPages = totalPages,
                totalResults = totalResults
            )
        }
}

class MovieDtoToEntityListMapper @Inject constructor(mapper: MovieDtoToEntityMapper) :
    ListMapper<MovieDto, MovieEntity>(mapper)

class MovieDtoToEntityMapper @Inject constructor() : IMapper<MovieDto, MovieEntity> {
    override fun map(input: MovieDto) =
        with(input) {
            MovieEntity(
                id = id,
                adult = adult,
                backdropPath = backdropPath,
                genreIds = genreIds,
                originalLanguage = originalLanguage,
                originalTitle = originalTitle,
                overview = overview,
                popularity = popularity,
                posterPath = posterPath,
                releaseDate = releaseDate,
                title = title,
                video = video,
                voteAverage = voteAverage,
                voteCount = voteCount
            )
        }
}

class DatesDtoToEntityMapper @Inject constructor() : IMapper<DatesDto, DatesEntity> {
    override fun map(input: DatesDto) = DatesEntity(input.maximum, input.minimum)
}
