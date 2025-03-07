package com.github.af2905.movieland.detail.tvshowdetail.presentation.state

import com.github.af2905.movieland.core.data.database.entity.CreditsCast
import com.github.af2905.movieland.core.data.database.entity.TvShow
import com.github.af2905.movieland.core.data.database.entity.TvShowDetail
import com.github.af2905.movieland.core.data.database.entity.TvShowType
import com.github.af2905.movieland.core.data.database.entity.Video

data class TvShowDetailsState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val tvShow: TvShowDetail? = null,
    val similarTvShows: List<TvShow> = emptyList(),
    val recommendedTvShows: List<TvShow> = emptyList(),
    val videos: List<Video> = emptyList(),
    val casts: List<CreditsCast> = emptyList(),
    val tvShowSocialIds: TvShowSocialIds = TvShowSocialIds()
) {
    data class TvShowSocialIds(
        val wikidataId: String? = null,
        val facebookId: String? = null,
        val instagramId: String? = null,
        val twitterId: String? = null
    )
}

sealed interface TvShowDetailsAction {
    data object BackClick : TvShowDetailsAction
    data class OpenTvShowDetail(val tvShowId: Int) : TvShowDetailsAction
    data class OpenPersonDetail(val personId: Int) : TvShowDetailsAction
    data class OpenVideo(val videoId: String) : TvShowDetailsAction
    data class OpenTvShowsByType(val tvShowType: TvShowType) : TvShowDetailsAction
}

sealed interface TvShowDetailsEffect {
    data object NavigateBack : TvShowDetailsEffect
    data class NavigateToTvShowDetail(val tvShowId: Int) : TvShowDetailsEffect
    data class NavigateToPerson(val personId: Int) : TvShowDetailsEffect
    data class NavigateToVideo(val videoId: String) : TvShowDetailsEffect
    data class NavigateToTvShows(val tvShowId: Int, val tvShowType: TvShowType) :
        TvShowDetailsEffect
}