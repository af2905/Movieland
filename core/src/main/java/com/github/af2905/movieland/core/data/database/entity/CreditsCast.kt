package com.github.af2905.movieland.core.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "CreditsCast",
    foreignKeys = [
        ForeignKey(
            entity = MovieDetail::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("movieId")]
)
data class CreditsCast(
    @PrimaryKey val id : Int,
    val movieId: Int?, // Foreign key to link with MovieDetail
    val adult: Boolean?,
    val gender: Int?,
    val knownForDepartment: String?,
    val name: String?,
    val originalName: String?,
    val popularity: Double?,
    val profilePath: String?,
    val castId: Int?,
    val character: String?,
    val creditId: String?,
    val order: Int?
)