package com.github.af2905.movieland.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.af2905.movieland.core.data.database.entity.plain.CreditsCast

@Entity
data class MovieDetail(
    @PrimaryKey val id: Int,
    val adult: Boolean?,
    val budget: Int?,
    val genres: List<Genre>?,
    val homepage: String?,
    val imdbId: String?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val productionCompanies: List<ProductionCompany>?,
    val productionCountries: List<ProductionCountry>?,
    val releaseDate: String?,
    val revenue: Long?,
    val runtime: Int?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val backdropPath: String?,
    val posterPath: String?,
    val liked: Boolean,
    val creditsCasts: List<CreditsCast>,
    val similarMovies: List<Movie>
)

data class Genre(val id: Int, val name: String?)

data class ProductionCompany(
    val id: Int,
    val name: String?,
    val originCountry: String?,
    val logoPath: String?
)

data class ProductionCountry(val iso: String?, val name: String?)