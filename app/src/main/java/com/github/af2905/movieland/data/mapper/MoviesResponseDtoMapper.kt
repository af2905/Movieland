package com.github.af2905.movieland.data.mapper

import com.github.af2905.movieland.data.database.entity.DatesEntity
import com.github.af2905.movieland.data.database.entity.MovieEntity
import com.github.af2905.movieland.data.database.entity.MoviesResponseEntity
import com.github.af2905.movieland.data.dto.DatesDto
import com.github.af2905.movieland.data.dto.MovieDto
import com.github.af2905.movieland.data.dto.MoviesResponseDto
import com.github.af2905.movieland.helper.extension.getFullPathToImage
import com.github.af2905.movieland.helper.mapper.IMapper
import com.github.af2905.movieland.helper.mapper.IMovieResponseMapper
import com.github.af2905.movieland.helper.mapper.ListMapper
import com.github.af2905.movieland.helper.mapper.MovieResponseListMapper
import com.github.af2905.movieland.presentation.model.item.Dates
import com.github.af2905.movieland.presentation.model.item.MovieItem
import com.github.af2905.movieland.presentation.model.item.MoviesResponse
import javax.inject.Inject

class MoviesResponseDtoToEntityMapper @Inject constructor(
    private val datesMapper: DatesDtoToEntityMapper
) : IMovieResponseMapper<MoviesResponseDto, String, Long, MoviesResponseEntity> {
    override fun map(input: MoviesResponseDto, type: String, timeStamp: Long) =
        with(input) {
            MoviesResponseEntity(
                dates = dates?.let { datesMapper.map(it) },
                page = page,
                totalPages = totalPages,
                totalResults = totalResults,
                movieType = type,
                timeStamp = timeStamp
            )
        }
}

class MovieDtoToEntityListMapper @Inject constructor(mapper: MovieDtoToEntityMapper) :
    MovieResponseListMapper<MovieDto, String, Long, MovieEntity>(mapper)

class MovieDtoToEntityMapper @Inject constructor() :
    IMovieResponseMapper<MovieDto, String, Long,MovieEntity> {
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
                responseMovieType = type
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
                title = title,
                video = video,
                voteAverage = voteAverage,
                voteCount = voteCount,
                posterPath = input.posterPath.getFullPathToImage()
            )
        }
}

class DatesDtoToUiMapper @Inject constructor() : IMapper<DatesDto, Dates> {
    override fun map(input: DatesDto) = Dates(input.maximum, input.minimum)
}