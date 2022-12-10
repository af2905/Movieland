package com.github.af2905.movieland.core.common.model.item

import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.Model
import com.github.af2905.movieland.util.extension.fiveStarRating
import com.github.af2905.movieland.util.extension.getFullPathToImage
import com.github.af2905.movieland.util.extension.getYearFromReleaseDate

data class MovieItem(
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
    val responseMovieType: String? = null,
) : Model(VIEW_TYPE) {

    val posterFullPathToImage: String?
        get() = posterPath.getFullPathToImage()

    val voteAverageStar: Float?
        get() = voteAverage?.fiveStarRating()?.toFloat()

    val releaseYear: String?
        get() = releaseDate?.getYearFromReleaseDate()

    var voteAverageBackground = voteAverage?.let {
        when (voteAverage) {
            in RAD_RANGE -> R.drawable.bg_red_corners_8
            in GREEN_RANGE -> R.drawable.bg_green_corners_8
            in GRAY_RANGE -> R.drawable.bg_dark_grey_corner_8
            else -> R.drawable.bg_dark_grey_corner_8
        }
    }

    companion object {
        val VIEW_TYPE = R.layout.list_item_movie
        val RAD_RANGE = 0.1..5.0
        val GRAY_RANGE = 5.1..6.9
        val GREEN_RANGE = 7.0..10.0
    }

    fun interface Listener : ItemDelegate.Listener {
        fun onItemClicked(item: MovieItem, position: Int)
    }
}