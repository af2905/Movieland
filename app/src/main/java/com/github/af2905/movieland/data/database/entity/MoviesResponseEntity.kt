package com.github.af2905.movieland.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.af2905.movieland.helper.extension.getFullPathToImage

data class MoviesResponseEntity(
    val dates: DatesEntity?,
    val page: Int,
    val movies: List<MovieEntity>,
    val totalPages: Int,
    val totalResults: Int
)

data class DatesEntity(val maximum: String, val minimum: String)

@Entity
data class MovieEntity(
    @PrimaryKey val id: Int,
    val adult: Boolean?,
    val genreIds: List<Int>?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val movieType: String = "",
    val timeStamp: Long? = null
) {
    var backdropPath: String? = null
        get() = field.getFullPathToImage()
    var posterPath: String? = null
        get() = field.getFullPathToImage()
}

enum class MovieType {
    POPULAR, NOW_PLAYING, TOP_RATED, UPCOMING, RECOMMENDED, SIMILAR
}