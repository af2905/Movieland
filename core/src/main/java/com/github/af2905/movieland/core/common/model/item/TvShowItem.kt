package com.github.af2905.movieland.core.common.model.item

import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.Model
import com.github.af2905.movieland.util.extension.fiveStarRating
import com.github.af2905.movieland.util.extension.getFullPathToImage
import com.github.af2905.movieland.util.extension.getYearFromReleaseDate

data class TvShowItem(
    override val id: Int,
    val posterPath: String?,
    val popularity: Double?,
    val backdropPath: String?,
    val voteAverage: Double?,
    val overview: String?,
    val firstAirDate: String?,
    val originCountry: List<String>?,
    val genreIds: List<Int>?,
    val originalLanguage: String?,
    val voteCount: Int?,
    val name: String?,
    val originalName: String?
) : Model(VIEW_TYPE) {

    val posterFullPathToImage: String?
        get() = posterPath.getFullPathToImage() ?: backdropPath.getFullPathToImage()

    val voteAverageStar: Float?
        get() = voteAverage?.fiveStarRating()?.toFloat()

    val releaseYear: String?
        get() = firstAirDate?.getYearFromReleaseDate()

    companion object {
        val VIEW_TYPE = R.layout.list_item_tv_show
    }

    fun interface Listener : ItemDelegate.Listener {
        fun onItemClicked(item: TvShowItem)
    }
}