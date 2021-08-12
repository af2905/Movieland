package com.github.af2905.movieland.data.mapper

import com.github.af2905.movieland.data.database.entity.DatesEntity
import com.github.af2905.movieland.data.database.entity.MovieEntity
import com.github.af2905.movieland.data.database.entity.MoviesResponseEntity
import com.github.af2905.movieland.data.dto.DatesDto
import com.github.af2905.movieland.data.dto.MovieDto
import com.github.af2905.movieland.data.dto.MoviesResponseDto
import com.github.af2905.movieland.helper.mapper.IMapper
import com.github.af2905.movieland.helper.mapper.IMovieResponseMapper
import com.github.af2905.movieland.helper.mapper.MovieResponseListMapper
import javax.inject.Inject

class MoviesResponseDtoToEntityMapper @Inject constructor(
    private val datesMapper: DatesDtoToEntityMapper
) : IMovieResponseMapper<MoviesResponseDto, String, MoviesResponseEntity> {
    override fun map(input: MoviesResponseDto, type: String) =
        with(input) {
            MoviesResponseEntity(
                dates = dates?.let { datesMapper.map(it) },
                page = page,
                totalPages = totalPages,
                totalResults = totalResults,
                movieType = type
            )
        }
}

class MovieDtoToEntityListMapper @Inject constructor(mapper: MovieDtoToEntityMapper) :
    MovieResponseListMapper<MovieDto, String, MovieEntity>(mapper)

class MovieDtoToEntityMapper @Inject constructor() :
    IMovieResponseMapper<MovieDto, String, MovieEntity> {
    override fun map(input: MovieDto, type: String) =
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
                voteCount = voteCount,
                responseMovieType = type
            )
        }
}

class DatesDtoToEntityMapper @Inject constructor() : IMapper<DatesDto, DatesEntity> {
    override fun map(input: DatesDto) = DatesEntity(input.maximum, input.minimum)
}