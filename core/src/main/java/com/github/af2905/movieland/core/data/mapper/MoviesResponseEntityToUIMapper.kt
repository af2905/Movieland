package com.github.af2905.movieland.core.data.mapper

import com.github.af2905.movieland.core.common.mapper.IMapper
import com.github.af2905.movieland.core.common.mapper.ListMapper
import com.github.af2905.movieland.core.common.model.item.Dates
import com.github.af2905.movieland.core.common.model.item.MovieItem
import com.github.af2905.movieland.core.common.model.item.MoviesResponse
import com.github.af2905.movieland.core.data.database.entity.DatesEntity
import com.github.af2905.movieland.core.data.database.entity.MovieEntity
import com.github.af2905.movieland.core.data.database.entity.MoviesResponseEntity
import com.github.af2905.movieland.util.extension.fiveStarRating
import com.github.af2905.movieland.util.extension.getYearFromReleaseDate
import javax.inject.Inject

class MoviesResponseEntityToUIMapper @Inject constructor(
    private val datesMapper: DatesEntityToUIMapper,
    private val movieMapper: MovieEntityToUIListMapper,
) : IMapper<MoviesResponseEntity, MoviesResponse> {
    override fun map(input: MoviesResponseEntity) =
        with(input) {
            MoviesResponse(
                dates = dates?.let { datesMapper.map(it) },
                page = page,
                movies = movieMapper.map(input.movies),
                totalPages = totalPages,
                totalResults = totalResults
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
                voteAverageStar = voteAverage?.fiveStarRating()?.toFloat(),
                voteCount = voteCount,
                releaseYear = releaseDate?.getYearFromReleaseDate()
            )
        }
}

class DatesEntityToUIMapper @Inject constructor() : IMapper<DatesEntity, Dates> {
    override fun map(input: DatesEntity) = Dates(input.maximum, input.minimum)
}