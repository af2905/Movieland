package com.github.af2905.movieland.core.data.database.entity

data class SearchMultiResult(
    val id: Int,
    val mediaType: MediaType,  // Use Enum for safety
    val name: String?,
    val title: String?,
    val overview: String?,
    val backdropPath: String?,
    val firstAirDate: String?,
    val releaseDate: String?,
    val genreIds: List<Int>?,
    val originCountry: List<String>?,
    val originalLanguage: String?,
    val originalName: String?,
    val popularity: Double?,
    val posterPath: String?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val profilePath: String?,
    val knownFor: List<KnownFor>?
)