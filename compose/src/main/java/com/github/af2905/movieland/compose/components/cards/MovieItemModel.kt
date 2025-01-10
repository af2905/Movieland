package com.github.af2905.movieland.compose.components.cards

import com.github.af2905.movieland.util.extension.fiveStarRating
import com.github.af2905.movieland.util.extension.getFullPathToImage
import com.github.af2905.movieland.util.extension.getYearFromReleaseDate

data class MovieItemModel(
    val id: Int,
    val adult: Boolean?,
    val backdropPath: String?,
    val genreIds: List<Int>?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Int?
) {
    val posterFullPathToImage: String?
        get() = posterPath.getFullPathToImage() ?: backdropPath.getFullPathToImage()

    val backdropFullPathToImage: String?
        get() = backdropPath.getFullPathToImage()

    val voteAverageStar: Double?
        get() = voteAverage?.fiveStarRating()

    val releaseYear: String?
        get() = releaseDate?.getYearFromReleaseDate()
}