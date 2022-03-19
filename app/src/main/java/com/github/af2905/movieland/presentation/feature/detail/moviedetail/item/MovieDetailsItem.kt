package com.github.af2905.movieland.presentation.feature.detail.moviedetail.item

import com.github.af2905.movieland.helper.extension.empty
import com.github.af2905.movieland.helper.extension.getYearFromReleaseDate
import com.github.af2905.movieland.presentation.common.ItemDelegate
import java.util.*

private const val COMMA_SEPARATOR = ", "

data class MovieDetailsItem(
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
    private val releaseYear = releaseDate.getYearFromReleaseDate()

    private val genreList = genres
        ?.map { genre -> genre.name }
        ?.map { it.lowercase(Locale.getDefault()) }
        .orEmpty()
        .joinToString(COMMA_SEPARATOR)

    val titleNextInfo = StringBuilder().apply {
        append(releaseYear)
        append(COMMA_SEPARATOR)
        append(genreList)
    }

    interface Listener : ItemDelegate.Listener {
        fun onLikedClick(item: MovieDetailsItem)
        fun onBackClicked()
    }
}

data class Genre(val id: Int, val name: String)

data class ProductionCountry(val iso: String, val name: String)

data class ProductionCompany(
    val id: Int,
    val name: String,
    val originCountry: String,
    var logoPath: String?
)