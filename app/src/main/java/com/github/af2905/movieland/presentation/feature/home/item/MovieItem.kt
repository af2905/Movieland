package com.github.af2905.movieland.presentation.feature.home.item

import com.github.af2905.movieland.R
import com.github.af2905.movieland.data.entity.MovieEntity
import com.github.af2905.movieland.presentation.model.Model

data class MovieItem(
    val id: Int,
    val adult: Boolean,
    val backdropPath: String?,
    val genreIds: List<Int> = listOf(),
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
) : Model(VIEW_TYPE) {

    companion object {
        const val VIEW_TYPE = R.layout.item_list_movie

        fun map(item: MovieEntity) =
            MovieItem(
                item.id,
                item.adult,
                item.backdropPath,
                item.genreIds,
                item.originalLanguage,
                item.originalTitle,
                item.overview,
                item.popularity,
                item.posterPath,
                item.releaseDate,
                item.title,
                item.video,
                item.voteAverage,
                item.voteCount
            )

        fun map(list: List<MovieEntity>) = list.map { map(it) }
    }
}