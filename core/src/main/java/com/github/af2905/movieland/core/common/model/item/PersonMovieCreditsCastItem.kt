package com.github.af2905.movieland.core.common.model.item

import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.Model
import com.github.af2905.movieland.util.extension.fiveStarRating
import com.github.af2905.movieland.util.extension.getFullPathToImage
import com.github.af2905.movieland.util.extension.getYearFromReleaseDate

data class PersonMovieCreditsCastItem(
    override val id: Int,
    val adult: Boolean?,
    val backdropPath: String?,
    val genreIds: List<Int>?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val character: String?,
    val creditId: String?,
    val order: Int?
) : Model(VIEW_TYPE) {

    val posterFullPathToImage: String?
        get() = posterPath.getFullPathToImage()

    val backdropPathToImage: String?
        get() = backdropPath.getFullPathToImage()

    val voteAverageStar: Float?
        get() = voteAverage?.fiveStarRating()?.toFloat()

    private val releaseYear: String?
        get() = releaseDate?.getYearFromReleaseDate()

    val titleWithReleaseYear = StringBuilder().apply {
        append(title)
        append(SPACE)
        append(LEFT_BRACKET)
        append(releaseYear)
        append(RIGHT_BRACKET)
    }

    companion object {
        val VIEW_TYPE = R.layout.list_item_cast

        private const val SPACE = " "
        private const val LEFT_BRACKET = "("
        private const val RIGHT_BRACKET = ")"
    }

    fun interface Listener : ItemDelegate.Listener {
        fun onItemClicked(item: PersonMovieCreditsCastItem)
    }
}