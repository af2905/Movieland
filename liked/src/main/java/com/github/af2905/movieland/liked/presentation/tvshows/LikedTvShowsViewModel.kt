package com.github.af2905.movieland.liked.presentation.tvshows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.base.Container
import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.model.item.SimpleTextItem
import com.github.af2905.movieland.core.common.model.item.TvShowV2Item
import com.github.af2905.movieland.liked.R
import com.github.af2905.movieland.liked.domain.GetAllSavedTvShows
import javax.inject.Inject

class LikedTvShowsViewModel @Inject constructor(
    private val getAllSavedTvShows: GetAllSavedTvShows
) : ViewModel() {

    val container: Container<LikedTvShowsContract.State, LikedTvShowsContract.Effect> =
        Container(viewModelScope, LikedTvShowsContract.State.Loading)

    init {
        loadData()
    }

    fun loadData() {
        container.intent {
            val savedMovies = getAllSavedTvShows.invoke(Unit).getOrNull().orEmpty()

            val list = if (savedMovies.isNotEmpty()) {
                savedMovies.map { TvShowV2Item(it) }
            } else {
                listOf(SimpleTextItem(res = R.string.liked_tv_shows_empty_list_text))
            }

            container.reduce {
                LikedTvShowsContract.State.Content(list = list)
            }
        }
    }

    fun openDetail(itemId: Int) {
        container.intent {
            container.postEffect(LikedTvShowsContract.Effect.OpenTvShowDetail(Navigate { navigator ->
                (navigator as LikedTvShowsNavigator).forwardToTvShowDetail(itemId)
            }))
        }
    }
}