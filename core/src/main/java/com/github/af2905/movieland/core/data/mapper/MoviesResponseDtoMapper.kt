package com.github.af2905.movieland.core.data.mapper

import com.github.af2905.movieland.core.common.mapper.IMapper
import com.github.af2905.movieland.core.common.mapper.IMovieResponseMapper
import com.github.af2905.movieland.core.common.mapper.ListMapper
import com.github.af2905.movieland.core.common.mapper.MovieResponseListMapper
import com.github.af2905.movieland.core.common.model.item.Dates
import com.github.af2905.movieland.core.common.model.item.MovieItem
import com.github.af2905.movieland.core.common.model.item.MoviesResponse
import com.github.af2905.movieland.core.data.database.entity.DatesEntity
import com.github.af2905.movieland.core.data.database.entity.MovieEntity
import com.github.af2905.movieland.core.data.database.entity.MoviesResponseEntity
import com.github.af2905.movieland.core.data.dto.movie.DatesDto
import com.github.af2905.movieland.core.data.dto.movie.MovieDto
import com.github.af2905.movieland.core.data.dto.movie.MoviesResponseDto
import com.github.af2905.movieland.util.extension.fiveStarRating
import com.github.af2905.movieland.util.extension.getFullPathToImage
import com.github.af2905.movieland.util.extension.getYearFromReleaseDate

import javax.inject.Inject

class MoviesResponseDtoToEntityMapper @Inject constructor(
    private val datesMapper: DatesDtoToEntityMapper,
    private val movieMapper: MovieDtoToEntityMapper
) : IMovieResponseMapper<MoviesResponseDto, String, Long, MoviesResponseEntity> {
    override fun map(input: MoviesResponseDto, type: String, timeStamp: Long) =
        with(input) {
            MoviesResponseEntity(
                dates = dates?.let { datesMapper.map(it) },
                page = page,
                movies = movies.map { movieMapper.map(it, type, timeStamp) },
                totalPages = totalPages,
                totalResults = totalResults
            )
        }
}

class MovieDtoToEntityListMapper @Inject constructor(mapper: MovieDtoToEntityMapper) :
    MovieResponseListMapper<MovieDto, String, Long, MovieEntity>(mapper)

class MovieDtoToEntityMapper @Inject constructor() :
    IMovieResponseMapper<MovieDto, String, Long, MovieEntity> {
    override fun map(input: MovieDto, type: String, timeStamp: Long) =
        with(input) {
            MovieEntity(
                id = id,
                adult = adult,
                genreIds = genreIds,
                originalLanguage = originalLanguage,
                originalTitle = originalTitle,
                overview = overview,
                popularity = popularity,
                releaseDate = releaseDate,
                title = title,
                video = video,
                voteAverage = voteAverage,
                voteCount = voteCount,
                movieType = type,
                timeStamp = timeStamp
            )
        }.also {
            it.backdropPath = input.backdropPath
            it.posterPath = input.posterPath
        }
}

class DatesDtoToEntityMapper @Inject constructor() : IMapper<DatesDto, DatesEntity> {
    override fun map(input: DatesDto) = DatesEntity(input.maximum, input.minimum)
}

class MoviesResponseDtoToUiMapper @Inject constructor(
    private val movieListMapper: MovieDtoToUiListMapper,
    private val datesMapper: DatesDtoToUiMapper
) : IMapper<MoviesResponseDto, MoviesResponse> {

    override fun map(input: MoviesResponseDto): MoviesResponse = with(input) {
        MoviesResponse(
            dates = dates?.let { datesMapper.map(it) },
            page = page,
            totalPages = totalPages,
            totalResults = totalResults,
            movies = movies.let { movieListMapper.map(it) }
        )
    }
}

class MovieDtoToUiListMapper @Inject constructor(mapper: MovieDtoToUiMapper) :
    ListMapper<MovieDto, MovieItem>(mapper)

class MovieDtoToUiMapper @Inject constructor() :
    IMapper<MovieDto, MovieItem> {
    override fun map(input: MovieDto) =
        with(input) {
            MovieItem(
                id = id,
                adult = adult,
                backdropPath = backdropPath,
                genreIds = genreIds,
                originalLanguage = originalLanguage,
                originalTitle = originalTitle,
                overview = overview,
                popularity = popularity,
                releaseDate = releaseDate,
                releaseYear = releaseDate?.getYearFromReleaseDate(),
                title = title,
                video = video,
                voteAverage = voteAverage,
                voteAverageStar = voteAverage.fiveStarRating().toFloat(),
                voteCount = voteCount,
                posterPath = input.posterPath.getFullPathToImage()
            )
        }
}

class DatesDtoToUiMapper @Inject constructor() : IMapper<DatesDto, Dates> {
    override fun map(input: DatesDto) = Dates(input.maximum, input.minimum)
}