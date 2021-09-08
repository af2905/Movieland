package com.github.af2905.movieland.presentation.model.item

import com.github.af2905.movieland.helper.extension.getYearFromReleaseDate
import com.github.af2905.movieland.presentation.common.ItemAdapter
import java.util.*

data class MovieDetailsItem(
    val id: Int,
    val adult: Boolean,
    val budget: Int,
    val genres: List<Genre>?,
    val homepage: String,
    val imdbId: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val productionCompanies: List<ProductionCompany>?,
    val productionCountries: List<ProductionCountry>?,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteAverageStar: Float,
    val voteCount: Int,
    val backdropPath: String?,
    val posterPath: String?,
    val liked: Boolean = false,
) {
    private val releaseYear = releaseDate.getYearFromReleaseDate()
    private val genreList = genres?.map { genre -> genre.name }
        ?.map { it.lowercase(Locale.getDefault()) }.orEmpty().joinToString(COMMA_SEPARATOR)
    val titleNextInfo = StringBuilder().apply {
        append(releaseYear); append(COMMA_SEPARATOR); append(genreList)
    }

    interface Listener : ItemAdapter.Listener {
        fun onLikedClick(item: MovieDetailsItem)
        fun onBackClicked()
    }

    companion object {
        private const val COMMA_SEPARATOR = ", "
    }
}

data class Genre(val id: Int, val name: String)
data class ProductionCountry(val iso: String, val name: String)
data class ProductionCompany(
    val id: Int, val name: String, val originCountry: String, var logoPath: String?
)