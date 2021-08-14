package com.github.af2905.movieland.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.github.af2905.movieland.R
import com.github.af2905.movieland.helper.extension.getFullPathToImage

data class ResponseWithMovies(
    @Embedded val moviesResponseEntity: MoviesResponseEntity,
    @Relation(parentColumn = "movieType", entityColumn = "responseMovieType")
    val movies: List<MovieEntity> = listOf()
)

@Entity
data class MoviesResponseEntity(
    @Embedded val dates: DatesEntity?,
    val page: Int,
    val totalPages: Int,
    val totalResults: Int,
    @PrimaryKey val movieType: String = ""
)

data class DatesEntity(val maximum: String, val minimum: String)

@Entity
data class MovieEntity(
    @PrimaryKey val id: Int,
    val adult: Boolean,
    val backdropPath: String?,
    val genreIds: List<Int>?,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val releaseDate: String?,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    val responseMovieType: String = ""
) {
    var posterPath: String? = null
        get() = field.getFullPathToImage()
    var voteAverageBackground = when (voteAverage) {
        in 0.0..5.0 -> R.drawable.bg_red_corners_8
        in 5.1..6.9 -> R.drawable.bg_grey_corner_8
        else -> R.drawable.bg_green_corners_8
    }
}

enum class MovieType {
    POPULAR, NOW_PLAYING, TOP_RATED, UPCOMING, SIMILAR, RECOMMENDED
}