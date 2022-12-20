package com.github.af2905.movieland.core.common.model.item

import com.github.af2905.movieland.core.R
import com.github.af2905.movieland.core.common.ItemDelegate
import com.github.af2905.movieland.core.common.model.Model
import com.github.af2905.movieland.core.data.MediaType
import com.github.af2905.movieland.core.data.database.entity.plain.KnownFor
import com.github.af2905.movieland.util.extension.fiveStarRating
import com.github.af2905.movieland.util.extension.getFullPathToImage

private const val SEPARATOR = " | "
private const val MEDIA_TYPE_NAME_EMPTY = -1

data class SearchMultiItem(
    override val id: Int,
    val mediaType: String,
    val name: String?,
    val title: String?,
    val overview: String?,
    val backdropPath: String?,
    val firstAirDate: String?,
    val releaseDate: String?,
    val genreIds: List<Int>?,
    val originCountry: List<String>?,
    val originalLanguage: String?,
    val originalName: String?,
    val popularity: Double?,
    val posterPath: String?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val profilePath: String?,
    val knownFor: List<KnownFor>?
) : Model(VIEW_TYPE) {

    val posterFullPathToImage: String?
        get() = posterPath.getFullPathToImage() ?: backdropPath.getFullPathToImage()

    val profileFullPathToImage: String?
        get() = profilePath.getFullPathToImage()

    val voteAverageStar: Float?
        get() = voteAverage?.fiveStarRating()?.toFloat()


    val personType = mediaType == MediaType.PERSON.type
    val movieType = mediaType == MediaType.MOVIE.type || mediaType == MediaType.TV.type

    val nameOrTitle = when (mediaType) {
        MediaType.MOVIE.type -> title
        else -> name
    }

    val personKnowFor = knownFor?.filter { !it.title.isNullOrEmpty() }
        ?.map { it.title }
        .orEmpty()
        .joinToString(SEPARATOR)

    val mediaTypeName = when (mediaType) {
        MediaType.TV.type -> R.string.media_type_tv
        MediaType.MOVIE.type -> R.string.media_type_movie
        MediaType.PERSON.type -> R.string.media_type_person
        else -> MEDIA_TYPE_NAME_EMPTY
    }

    val mediaTypeVisible = mediaTypeName != MEDIA_TYPE_NAME_EMPTY

    companion object {
        val VIEW_TYPE = R.layout.list_item_search_multi
    }

    fun interface Listener : ItemDelegate.Listener {
        fun onItemClicked(item: SearchMultiItem)
    }
}