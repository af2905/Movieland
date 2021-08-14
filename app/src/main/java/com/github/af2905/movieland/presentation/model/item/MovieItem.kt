package com.github.af2905.movieland.presentation.model.item

import com.github.af2905.movieland.R
import com.github.af2905.movieland.presentation.model.Model

data class MovieItem(
    val id: Int,
    val adult: Boolean,
    val backdropPath: String?,
    val genreIds: List<Int>?,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    val responseMovieType: String,
    val voteAverageBackground: Int
) : Model(VIEW_TYPE) {

    companion object {
        const val VIEW_TYPE = R.layout.item_list_movie
    }
}