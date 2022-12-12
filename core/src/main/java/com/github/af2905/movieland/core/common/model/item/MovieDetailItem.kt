package com.github.af2905.movieland.core.common.model.item

import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.util.extension.empty
import com.github.af2905.movieland.util.extension.getFullPathToImage
import com.github.af2905.movieland.util.extension.getYearFromReleaseDate
import java.util.*

private const val COMMA_SEPARATOR = ", "

data class MovieDetailItem(
    val id: Int = Int.empty,
    val adult: Boolean = false,
    val budget: Int = Int.empty,
    val genres: List<Genre>? = null,
    val homepage: String = String.empty,
    val imdbId: String = String.empty,
    val originalLanguage: String = String.empty,
    val originalTitle: String = String.empty,
    val overview: String = String.empty,
    val popularity: Double = Double.empty,
    val productionCompanies: List<ProductionCompany>? = null,
    val productionCountries: List<ProductionCountry>? = null,
    val releaseDate: String = String.empty,
    val revenue: Int = Int.empty,
    val runtime: Int = Int.empty,
    val status: String = String.empty,
    val tagline: String = String.empty,
    val title: String = String.empty,
    val video: Boolean = false,
    val voteAverage: Double = Double.empty,
    val voteAverageStar: Float = Float.empty,
    val voteCount: Int = Int.empty,
    val backdropPath: String? = null,
    val posterPath: String? = null,
    val liked: Boolean = false
) {
    val backdropFullPathToImage: String?
        get() = backdropPath.getFullPathToImage()

    val posterFullPathToImage: String?
        get() = posterPath.getFullPathToImage()

    private val releaseYear = releaseDate.getYearFromReleaseDate()

    private val genreList = genres?.map { genre -> genre.name }
        ?.map { it.lowercase(Locale.getDefault()) }
        .orEmpty()
        .joinToString(COMMA_SEPARATOR)

    val titleNextInfo = StringBuilder().apply {
        append(releaseYear)
        append(COMMA_SEPARATOR)
        append(genreList)
    }

    val taglineVisible: Boolean = tagline.isNotEmpty()

    fun interface Listener : ItemDelegate.Listener {
        fun onLikedClick(item: MovieDetailItem)
    }

    companion object {
        fun MovieDetailItem.mapToMovieItem() = with(this) {
            MovieItem(
                id = id,
                adult = adult,
                backdropPath = backdropPath,
                genreIds = genres?.map { it.id },
                originalLanguage = originalLanguage,
                originalTitle = originalTitle,
                overview = overview,
                popularity = popularity,
                posterPath = posterPath,
                releaseDate = releaseDate,
                title = title,
                video = video,
                voteAverage = voteAverage,
                voteCount = voteCount,
                responseMovieType = null
            )
        }
    }
}

data class Genre(val id: Int, val name: String)

data class ProductionCountry(val iso: String, val name: String)

data class ProductionCompany(
    val id: Int,
    val name: String,
    val originCountry: String,
    val logoPath: String?
) {
    val logoFullPathToImage: String?
        get() = logoPath.getFullPathToImage()
}