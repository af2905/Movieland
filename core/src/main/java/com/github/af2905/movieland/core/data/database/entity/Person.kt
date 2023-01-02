package com.github.af2905.movieland.core.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

private const val KNOWN_FOR_ID = "known_for_id"
private const val KNOWN_FOR_NAME = "known_for_name"
private const val KNOWN_FOR_TITLE = "known_for_title"
private const val KNOWN_FOR_BACKDROP_PATH = "known_for_backdrop_path"
private const val KNOWN_FOR_FIRST_AIR_DATE = "known_for_firstAirDate"
private const val KNOWN_FOR_GENRE_IDS = "known_for_genreIds"
private const val KNOWN_FOR_RELEASE_DATE = "known_for_releaseDate"
private const val KNOWN_FOR_MEDIA_TYPE = "known_for_mediaType"
private const val KNOWN_FOR_ORIGIN_COUNTRY = "known_for_originCountry"
private const val KNOWN_FOR_ORIGINAL_LANGUAGE = "known_for_originalLanguage"
private const val KNOWN_FOR_ORIGINAL_NAME = "known_for_originalName"
private const val KNOWN_FOR_OVERVIEW = "known_for_overview"
private const val KNOWN_FOR_POSTER_PATH = "known_for_posterPath"
private const val KNOWN_FOR_VOTE_AVERAGE = "known_for_voteAverage"
private const val KNOWN_FOR_VOTE_COUNT = "known_for_voteCount"

@Entity
data class Person(
    @PrimaryKey val id: Int,
    val knownFor: List<KnownFor>?,
    val name: String?,
    val profilePath: String?,
    val popularity: Double?,
    val timeStamp: Long? = null
)

data class KnownFor(
    @ColumnInfo(name = KNOWN_FOR_ID) val id: Int,
    @ColumnInfo(name = KNOWN_FOR_NAME) val name: String?,
    @ColumnInfo(name = KNOWN_FOR_TITLE) val title: String?,
    @ColumnInfo(name = KNOWN_FOR_BACKDROP_PATH) val backdropPath: String?,
    @ColumnInfo(name = KNOWN_FOR_FIRST_AIR_DATE) val firstAirDate: String?,
    @ColumnInfo(name = KNOWN_FOR_GENRE_IDS) val genreIds: List<Int>?,
    @ColumnInfo(name = KNOWN_FOR_RELEASE_DATE) val releaseDate: String?,
    @ColumnInfo(name = KNOWN_FOR_MEDIA_TYPE) val mediaType: String,
    @ColumnInfo(name = KNOWN_FOR_ORIGIN_COUNTRY) val originCountry: List<String>?,
    @ColumnInfo(name = KNOWN_FOR_ORIGINAL_LANGUAGE) val originalLanguage: String?,
    @ColumnInfo(name = KNOWN_FOR_ORIGINAL_NAME) val originalName: String?,
    @ColumnInfo(name = KNOWN_FOR_OVERVIEW) val overview: String?,
    @ColumnInfo(name = KNOWN_FOR_POSTER_PATH) val posterPath: String?,
    @ColumnInfo(name = KNOWN_FOR_VOTE_AVERAGE) val voteAverage: Double?,
    @ColumnInfo(name = KNOWN_FOR_VOTE_COUNT) val voteCount: Int?
)