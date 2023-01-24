package com.github.af2905.movieland.core.common.model.item

import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.util.extension.empty
import com.github.af2905.movieland.util.extension.formatDate
import com.github.af2905.movieland.util.extension.getFullPathToImage
import java.util.*

private const val SEPARATOR = ", "

data class TvShowDetailItem(
    val id: Int,
    val lastEpisodeToAir: LastEpisodeToAir?,
    val backdropPath: String?,
    val createdBy: List<CreatedBy>?,
    val episodeRunTime: List<Int>?,
    val firstAirDate: String?,
    val genres: List<Genre>?,
    val homepage: String?,
    val inProduction: Boolean?,
    val languages: List<String>,
    val lastAirDate: String?,
    val name: String?,
    val networks: List<Network>?,
    val numberOfEpisodes: Int?,
    val numberOfSeasons: Int?,
    val originCountry: List<String>?,
    val originalLanguage: String?,
    val originalName: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val productionCompanies: List<ProductionCompany>?,
    val productionCountries: List<ProductionCountry>?,
    val seasons: List<Season>?,
    val status: String?,
    val tagline: String?,
    val type: String?,
    val voteCount: Int?,
    val voteAverage: Double? = Double.empty,
    val voteAverageStar: Float? = Float.empty,
    val liked: Boolean = false,
    val creditsCasts: List<CreditsCastItem> = emptyList(),
    val similarTvShows: List<TvShowItem> = emptyList()
) {
    val backdropFullPathToImage: String?
        get() = backdropPath.getFullPathToImage() ?: posterPath.getFullPathToImage()

    val firstAirDateFormatted = firstAirDate?.formatDate()
    val lastAirDateFormatted = lastAirDate?.formatDate()

    val genreList = genres?.map { genre -> genre.name }
        ?.map { it?.lowercase(Locale.getDefault()) }
        .orEmpty()
        .joinToString(SEPARATOR)

    val taglineVisible = tagline.isNullOrEmpty().not()
    val overviewVisible = overview.isNullOrEmpty().not()
    val firstAirDateVisible = firstAirDate.isNullOrEmpty().not()
    val lastAirDateVisible = lastAirDate.isNullOrEmpty().not()

    fun interface Listener : ItemDelegate.Listener {
        fun onLikedClick(item: TvShowDetailItem)
    }

    companion object {
        fun TvShowDetailItem.mapToTvShowItem() = with(this) {
            TvShowItem(
                id = id,
                posterPath = posterPath,
                popularity = popularity,
                backdropPath = backdropPath,
                voteAverage = voteAverage,
                overview = overview,
                firstAirDate = firstAirDate,
                originCountry = originCountry,
                genreIds = genres?.map { it.id },
                originalLanguage = originalLanguage,
                voteCount = voteCount,
                name = name,
                originalName = originalName
            )
        }
    }
}

data class LastEpisodeToAir(
    val id: Int,
    val airDate: String?,
    val episodeNumber: Int?,
    val name: String?,
    val overview: String?,
    val productionCode: String?,
    val seasonNumber: Int?,
    val stillPath: String?,
    val voteAverage: Double?,
    val voteCount: Int?
)

data class CreatedBy(
    val id: Int,
    val creditId: String?,
    val name: String?,
    val gender: Int?,
    val profilePath: String?
)

data class Network(
    val id: Int,
    val name: String?,
    val logoPath: String?,
    val originCountry: String?
)

data class Season(
    val id: Int,
    val airDate: String?,
    val episodeCount: Int?,
    val name: String?,
    val overview: String?,
    val posterPath: String?,
    val seasonNumber: Int?
)