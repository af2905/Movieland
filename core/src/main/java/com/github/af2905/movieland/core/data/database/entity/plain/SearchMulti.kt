package com.github.af2905.movieland.core.data.database.entity.plain

data class SearchMulti(
    val id: Int,
    val mediaType: String,
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
    val knownFor: List<KnownFor>?,
)

data class KnownFor(
    val id: Int,
    val name: String?,
    val title: String?,
    val backdropPath: String?,
    val firstAirDate: String?,
    val genreIds: List<Int>?,
    val releaseDate: String?,
    val mediaType: String,
    val originCountry: List<String>?,
    val originalLanguage: String?,
    val originalName: String?,
    val overview: String?,
    val posterPath: String?,
    val voteAverage: Double?,
    val voteCount: Int?
)