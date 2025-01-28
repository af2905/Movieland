package com.github.af2905.movieland.core.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class MovieDetail(
    @PrimaryKey val id: Int,
    val title: String?,
    val originalTitle: String?,
    val originalLanguage: String?,
    val overview: String?,
    val tagline: String?,
    val budget: Int?,
    val revenue: Long?,
    val runtime: Int?,
    val releaseDate: String?,
    val status: String?,
    val adult: Boolean?,
    val popularity: Double?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val homepage: String?,
    val backdropPath: String?,
    val posterPath: String?,
    val video: Boolean?,
    val genres: List<Genre>?,
    val productionCompanies: List<ProductionCompany>?,
    val productionCountries: List<ProductionCountry>?,
)

data class MovieDetailWithDetails(
    @Embedded val movie: MovieDetail,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    ) val productionCompanies: List<ProductionCompany>,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    ) val productionCountries: List<ProductionCountry>,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    ) val cast: List<CreditsCast>
)