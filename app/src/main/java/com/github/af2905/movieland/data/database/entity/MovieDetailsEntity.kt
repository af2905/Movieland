package com.github.af2905.movieland.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.af2905.movieland.helper.extension.getFullPathToImage

@Entity
data class MovieDetailsEntity(
    @PrimaryKey val id: Int,
    val adult: Boolean,
    val budget: Int,
    val genres: List<GenreEntity>?,
    val homepage: String,
    val imdbId: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
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
) {
    var backdropPath: String? = null
        get() = field.getFullPathToImage()

    var posterPath: String? = null
        get() = field.getFullPathToImage()
}

data class GenreEntity(val id: Int, val name: String)

data class ProductionCompanyEntity(val id: Int, val name: String, val originCountry: String) {
    var logoPath: String? = null
        get() = field.getFullPathToImage()
}

data class ProductionCountryEntity(val iso: String, val name: String)