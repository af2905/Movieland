package com.github.af2905.movieland.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieDetailsEntity(
    @PrimaryKey val id: Int,
    val adult: Boolean,
    val backdropPath: String?,
    val budget: Int,
    val genres: List<GenreEntity>?,
    val homepage: String,
    val imdbId: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val productionCompanies: List<ProductionCompanyEntity>?,
    val productionCountries: List<ProductionCountryEntity>?,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)

data class GenreEntity(
    val id: Int,
    val name: String
)

data class ProductionCompanyEntity(
    val id: Int,
    val logoPath: String?,
    val name: String,
    val originCountry: String
)

data class ProductionCountryEntity(
    val iso: String,
    val name: String,
)