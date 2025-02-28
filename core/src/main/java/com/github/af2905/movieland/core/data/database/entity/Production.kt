package com.github.af2905.movieland.core.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "production_companies",
    foreignKeys = [
        ForeignKey(
            entity = MovieDetail::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["movieId"])]
)
data class ProductionCompany(
    @PrimaryKey val companyId: Int,
    val movieId: Int, // Foreign key to MovieDetail
    val companyName: String,
    val logoPath: String?,
    val originCountry: String
)

@Entity(
    tableName = "production_countries",
    foreignKeys = [
        ForeignKey(
            entity = MovieDetail::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["movieId"])]
)
data class ProductionCountry(
    @PrimaryKey val countryId: Int,
    val movieId: Int, // Foreign key to MovieDetail
    val countryName: String,
    val isoCode: String
)