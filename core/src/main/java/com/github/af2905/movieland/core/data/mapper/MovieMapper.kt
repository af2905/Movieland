package com.github.af2905.movieland.core.data.mapper

import com.github.af2905.movieland.core.common.mapper.ListMapperImpl
import com.github.af2905.movieland.core.common.mapper.Mapper
import com.github.af2905.movieland.core.common.mapper.MovieResponseListMapperImpl
import com.github.af2905.movieland.core.common.mapper.MovieResponseMapper
import com.github.af2905.movieland.core.common.model.item.MovieItem
import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.dto.movie.MovieDto
import javax.inject.Inject

class MovieDtoToMovieListMapper @Inject constructor(
    mapper: MovieDtoToMovieMapper
) : ListMapperImpl<MovieDto, Movie>(mapper)

class MovieDtoToMovieMapper @Inject constructor() : Mapper<MovieDto, Movie> {
    override fun map(input: MovieDto) =
        with(input) {
            Movie(
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
                backdropPath = backdropPath,
                posterPath = posterPath
            )
        }
}

class MovieResponseDtoToMovieListMapper @Inject constructor(
    mapper: MovieResponseDtoToMovieMapper
) : MovieResponseListMapperImpl<MovieDto, String, Long, Movie>(mapper)

class MovieResponseDtoToMovieMapper @Inject constructor() :
    MovieResponseMapper<MovieDto, String, Long, Movie> {
    override fun map(input: MovieDto, type: String, timeStamp: Long) =
        with(input) {
            Movie(
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
                backdropPath = backdropPath,
                posterPath = posterPath,
                movieType = type,
                timeStamp = timeStamp
            )
        }
}

class MovieToMovieItemListMapper @Inject constructor(
    mapper: MovieToMovieItemMapper
) : ListMapperImpl<Movie, MovieItem>(mapper)

class MovieToMovieItemMapper @Inject constructor() :
    Mapper<Movie, MovieItem> {
    override fun map(input: Movie) = with(input) {
        MovieItem(
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
        )
    }
}

class MovieItemToMovieListMapper @Inject constructor(
    mapper: MovieItemToMovieMapper
) : ListMapperImpl<MovieItem, Movie>(mapper)

class MovieItemToMovieMapper @Inject constructor() :
    Mapper<MovieItem, Movie> {
    override fun map(input: MovieItem) = with(input) {
        Movie(
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
        )
    }
}