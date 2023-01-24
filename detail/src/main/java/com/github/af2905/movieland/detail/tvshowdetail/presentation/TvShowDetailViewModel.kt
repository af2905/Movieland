package com.github.af2905.movieland.detail.tvshowdetail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.base.Container
import com.github.af2905.movieland.core.common.ErrorHandler
import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.effect.ToastMessage
import com.github.af2905.movieland.core.common.model.ItemIds
import com.github.af2905.movieland.core.common.model.Model
import com.github.af2905.movieland.core.common.model.item.*
import com.github.af2905.movieland.detail.R
import com.github.af2905.movieland.detail.tvshowdetail.TvShowDetailNavigator
import com.github.af2905.movieland.detail.usecase.tvshow.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import javax.inject.Inject

private const val ACTORS_LIST_ID = ItemIds.HORIZONTAL_ITEM_LIST_ID * 1000 + 1
private const val SIMILAR_TV_SHOWS_LIST_ID = ItemIds.HORIZONTAL_ITEM_LIST_ID * 1000 + 2

class TvShowDetailViewModel @Inject constructor(
    private val tvShowId: Int,
    private val getTvShowDetail: GetTvShowDetail,
    private val getTvShowCredits: GetTvShowCredits,
    private val getSimilarTvShows: GetSimilarTvShows,
    private val saveToLikedTvShow: SaveToLikedTvShow,
    private val removeFromLikedTvShow: RemoveFromLikedTvShow,
    private val getLikedTvShowById: GetLikedTvShowById
) : ViewModel() {

    val container: Container<TvShowDetailContract.State, TvShowDetailContract.Effect> =
        Container(viewModelScope, TvShowDetailContract.State.Loading)

    private val emptySpaceNormal = EmptySpaceItem(R.dimen.default_margin)
    private val emptySpaceBig = EmptySpaceItem(R.dimen.default_margin_big)
    private val emptySpaceHuge = EmptySpaceItem(R.dimen.default_margin_huge)

    val tvShowDetailItemClickListener = TvShowDetailItem.Listener {
        container.intent {
            if (this.state.value is TvShowDetailContract.State.Content) {
                val contentState = this.state.value as TvShowDetailContract.State.Content
                handleLikeTvShow(contentState)
            }
        }
    }

    val errorItemClickListener = ErrorItem.Listener { loadData() }
    val backButtonItemClickListener = BackButtonItem.Listener { openPreviousScreen() }

    init {
        loadData()
    }

    private suspend fun handleLikeTvShow(state: TvShowDetailContract.State.Content) {
        if (state.tvShowDetailItem.liked) {
            removeFromLikedTvShow(UnlikedTvShowDetailParams(state.tvShowDetailItem))
        } else {
            saveToLikedTvShow(
                LikedTvShowDetailParams(
                    state.tvShowDetailItem.copy(
                        liked = !state.tvShowDetailItem.liked
                    )
                )
            )
        }
        container.reduce {
            state.copy(
                tvShowDetailItem = state.tvShowDetailItem.copy(
                    liked = !state.tvShowDetailItem.liked
                ),
                list = state.list
            )
        }
        container.postEffect(
            TvShowDetailContract.Effect.LikeClicked
        )
    }

    private fun loadData() {
        container.intent {
            if (container.state.value !is TvShowDetailContract.State.Loading) {
                container.reduce { TvShowDetailContract.State.Loading }
            }
            try {
                handleTvShowDetail(viewModelScope)
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private suspend fun handleTvShowDetail(scope: CoroutineScope) {
        val list = mutableListOf<Model>()
        var tvShowDetailItem = getLikedTvShowById(LikedTvShowByIdParams(tvShowId)).getOrThrow()

        if (tvShowDetailItem == null) {
            val movieDetailAsync = scope.async {
                getTvShowDetail(TvShowDetailParams(tvShowId)).getOrThrow()
            }
            val tvShowCreditsCastAsync = scope.async {
                getTvShowCredits(TvShowCreditsParams(tvShowId)).getOrDefault(emptyList())
            }
            val similarTvShowsAsync = scope.async {
                getSimilarTvShows.invoke(SimilarTvShowsParams(tvShowId)).getOrDefault(emptyList())
            }

            tvShowDetailItem = movieDetailAsync.await()
            val tvShowCreditsCasts = tvShowCreditsCastAsync.await()
            val similarTvShows = similarTvShowsAsync.await()

            val movieCreditCastsBlock = createActorsAndCrewBlock(tvShowCreditsCasts)
            val similarMoviesBlock = createSimilarTvShowsBlock(similarTvShows)

            list.add(TvShowDetailDescItem(tvShowDetailItem))
            list.addAll(movieCreditCastsBlock)
            list.addAll(similarMoviesBlock)
            tvShowDetailItem = tvShowDetailItem.copy(
                creditsCasts = tvShowCreditsCasts,
                similarTvShows = similarTvShows
            )
        } else {
            list.add(TvShowDetailDescItem(tvShowDetailItem))
            list.addAll(createActorsAndCrewBlock(tvShowDetailItem.creditsCasts))
            list.addAll(createSimilarTvShowsBlock(tvShowDetailItem.similarTvShows))
        }
        container.reduce {
            TvShowDetailContract.State.Content(tvShowDetailItem = tvShowDetailItem, list = list)
        }
    }

    private fun createActorsAndCrewBlock(tvShowCreditsCasts: List<CreditsCastItem>): List<Model> {
        return if (tvShowCreditsCasts.isNotEmpty()) {
            listOf(
                emptySpaceNormal,
                HeaderItem(R.string.actors_and_crew_title),
                emptySpaceNormal,
                HorizontalListItem(tvShowCreditsCasts, id = ACTORS_LIST_ID),
                emptySpaceBig
            )
        } else {
            emptyList()
        }
    }

    private fun createSimilarTvShowsBlock(similarTvShows: List<TvShowItem>): List<Model> {
        return if (similarTvShows.isNotEmpty()) {
            listOf(
                HeaderItem(R.string.similar),
                emptySpaceNormal,
                HorizontalListItem(list = similarTvShows, id = SIMILAR_TV_SHOWS_LIST_ID),
                emptySpaceHuge
            )
        } else {
            emptyList()
        }
    }

    private suspend fun handleError(e: Exception) {
        container.reduce {
            TvShowDetailContract.State.Error(
                errorItem = ErrorItem(errorMessage = e.message.orEmpty()),
                e = e
            )
        }
        container.intent {
            container.postEffect(
                TvShowDetailContract.Effect.ShowFailMessage(
                    ToastMessage(ErrorHandler.handleError(e))
                )
            )
        }
    }

    fun navigateToTvShowDetail(itemId: Int) {
        container.intent {
            container.postEffect(TvShowDetailContract.Effect.OpenTvShowDetail(Navigate { navigator ->
                (navigator as TvShowDetailNavigator).forwardToTvShowDetail(itemId)
            }))
        }
    }

    fun navigateToPersonDetail(itemId: Int) {
        container.intent {
            container.postEffect(TvShowDetailContract.Effect.OpenPersonDetail(Navigate { navigator ->
                (navigator as TvShowDetailNavigator).forwardToPersonDetail(itemId)
            }))
        }
    }

    private fun openPreviousScreen() {
        container.intent {
            container.postEffect(TvShowDetailContract.Effect.OpenPreviousScreen(Navigate { navigator ->
                navigator.back()
            }))
        }
    }
}