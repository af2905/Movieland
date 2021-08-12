package com.github.af2905.movieland.data.mapper

import com.github.af2905.movieland.data.database.entity.DatesEntity
import com.github.af2905.movieland.data.database.entity.MovieEntity
import com.github.af2905.movieland.data.database.entity.ResponseWithMovies
import com.github.af2905.movieland.helper.mapper.IMapper
import com.github.af2905.movieland.helper.mapper.ListMapper
import com.github.af2905.movieland.presentation.model.item.Dates
import com.github.af2905.movieland.presentation.model.item.MovieItem
import com.github.af2905.movieland.presentation.model.item.MoviesResponse
import javax.inject.Inject

class MoviesResponseEntityMapper @Inject constructor(
    private val datesMapper: DatesEntityToUIMapper,
    private val movieMapper: MovieEntityToUIListMapper,
) : IMapper<ResponseWithMovies, MoviesResponse> {
    override fun map(input: ResponseWithMovies) =
        with(input) {
            MoviesResponse(
                dates = moviesResponseEntity.dates?.let { datesMapper.map(it) },
                page = moviesResponseEntity.page,
                movies = movieMapper.map(input.movies),
                totalPages = moviesResponseEntity.totalPages,
                totalResults = moviesResponseEntity.totalResults,
                movieType = moviesResponseEntity.movieType
            )
        }
}

class MovieEntityToUIListMapper @Inject constructor(mapper: MovieEntityToUIMapper) :
    ListMapper<MovieEntity, MovieItem>(mapper)

class MovieEntityToUIMapper @Inject constructor() :
    IMapper<MovieEntity, MovieItem> {
    override fun map(input: MovieEntity) =
        with(input) {
            MovieItem(
                id = id,
                adult = adult,
                backdropPath = backdropPath,
                //genreIds = genreIds,
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
                responseMovieType = responseMovieType
            )
        }
}

class DatesEntityToUIMapper @Inject constructor() : IMapper<DatesEntity, Dates> {
    override fun map(input: DatesEntity) = Dates(input.maximum, input.minimum)
}