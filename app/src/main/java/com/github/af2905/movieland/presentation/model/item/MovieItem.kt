package com.github.af2905.movieland.presentation.model.item

import com.github.af2905.movieland.R
import com.github.af2905.movieland.presentation.common.ItemAdapter
import com.github.af2905.movieland.presentation.model.Model

data class MovieItem(
    val id: Int,
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
    val responseMovieType: String?,
) : Model(VIEW_TYPE) {

    var voteAverageBackground = voteAverage?.let { when (voteAverage) {
        in RAD_RANGE -> R.drawable.bg_red_corners_8
        in GRAY_RANGE -> R.drawable.bg_grey_corner_8
        else -> R.drawable.bg_green_corners_8
    } }

    companion object {
        const val VIEW_TYPE = R.layout.list_item_movie
        val RAD_RANGE = 0.0..5.0
        val GRAY_RANGE = 5.1..6.9
    }

    fun interface Listener : ItemAdapter.Listener {
        fun onItemClicked(item: MovieItem, position: Int)
    }
}