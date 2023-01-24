package com.github.af2905.movieland.core.common.model.item

import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.util.extension.empty
import com.github.af2905.movieland.util.extension.getFullPathToImage
import com.github.af2905.movieland.util.extension.getYearFromReleaseDate
import java.util.*

private const val SEPARATOR = ", "

data class MovieDetailItem(
    val id: Int,
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
    val voteCount: Int?,
    val backdropPath: String?,
    val posterPath: String?,
    val voteAverage: Double? = Double.empty,
    val voteAverageStar: Float? = Float.empty,
    val liked: Boolean = false,
    val creditsCasts: List<CreditsCastItem> = emptyList(),
    val similarMovies: List<MovieItem> = emptyList()
) {
    val backdropFullPathToImage: String?
        get() = backdropPath.getFullPathToImage() ?: posterPath.getFullPathToImage()

    private val releaseYear = releaseDate?.getYearFromReleaseDate()

    private val genreList = genres?.map { genre -> genre.name }
        ?.map { it?.lowercase(Locale.getDefault()) }
        .orEmpty()
        .joinToString(SEPARATOR)

    val titleNextInfo = StringBuilder().apply {
        releaseYear?.let {
            append(releaseYear)
        }
        if (!releaseYear.isNullOrEmpty() && genreList.isNotEmpty()) {
            append(SEPARATOR)
        }
        if (genreList.isEmpty().not()) {
            append(genreList)
        }
    }

    val taglineVisible = tagline.isNullOrEmpty().not()
    val overviewVisible = overview.isNullOrEmpty().not()

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
                voteCount = voteCount
            )
        }
    }
}

data class Genre(val id: Int, val name: String?)

data class ProductionCountry(val iso: String?, val name: String?)

data class ProductionCompany(
    val id: Int,
    val name: String?,
    val originCountry: String?,
    val logoPath: String?
) {
    val logoFullPathToImage: String?
        get() = logoPath.getFullPathToImage()
}