package com.github.af2905.movieland.core.data.mapper

import com.github.af2905.movieland.core.common.model.item.MovieItem
import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.database.entity.MovieType
import com.github.af2905.movieland.core.data.dto.movie.MovieDto
import javax.inject.Inject

class MovieMapper @Inject constructor() {

    @JvmName(DTO_TO_ENTITY_MAPPER)
    fun map(input: List<MovieDto>): List<Movie> = input.map { dto -> map(dto) }

    fun map(input: List<MovieDto>, type: MovieType, timeStamp: Long): List<Movie> =
        input.map { dto -> map(dto, type, timeStamp) }

    @JvmName(ENTITY_TO_UI_ITEM_MAPPER)
    fun map(input: List<Movie>): List<MovieItem> = input.map { entity -> map(entity) }

    @JvmName(UI_ITEM_TO_ENTITY_MAPPER)
    fun map(input: List<MovieItem>): List<Movie> = input.map { item -> map(item) }

    private fun map(input: MovieDto): Movie = with(input) {
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

    private fun map(input: MovieDto, type: MovieType, timeStamp: Long): Movie =
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

    private fun map(input: Movie): MovieItem = with(input) {
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

    private fun map(input: MovieItem): Movie = with(input) {
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