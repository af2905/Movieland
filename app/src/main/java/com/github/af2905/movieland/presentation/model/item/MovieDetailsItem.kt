package com.github.af2905.movieland.presentation.model.item

import com.github.af2905.movieland.R
import com.github.af2905.movieland.presentation.common.ItemAdapter
import com.github.af2905.movieland.presentation.model.Model

data class MovieDetailsItem(
    val id: Int,
    val adult: Boolean,
    val backdropPath: String?,
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
    val voteCount: Int,
    val posterPath: String?
) : Model(VIEW_TYPE) {

    companion object {
        const val VIEW_TYPE = R.layout.list_item_movie_details
    }

    fun interface Listener : ItemAdapter.Listener {
        fun click(item: MovieDetailsItem)
    }
}

data class Genre(val id: Int, val name: String)
data class ProductionCountry(val iso: String, val name: String)
data class ProductionCompany(
    val id: Int, val name: String, val originCountry: String, var logoPath: String?
)