package com.github.af2905.movieland.tvshows.presentation.topRatedTvShows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.base.Container
import com.github.af2905.movieland.core.common.effect.Navigate
import com.github.af2905.movieland.core.common.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.core.common.model.item.ErrorItem
import com.github.af2905.movieland.core.common.model.item.TvShowV2Item
import com.github.af2905.movieland.core.data.database.entity.TvShowType
import com.github.af2905.movieland.core.shared.CachedTvShowsParams
import com.github.af2905.movieland.core.shared.GetCachedTvShowsByType
import com.github.af2905.movieland.core.shared.GetTopRatedTvShows
import com.github.af2905.movieland.core.shared.TopRatedTvShowsParams
import com.github.af2905.movieland.tvshows.presentation.TvShowsNavigator
import com.github.af2905.movieland.tvshows.repository.TvShowsForceUpdateRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopRatedTvShowsViewModel @Inject constructor(
    private val getTopRatedTvShows: GetTopRatedTvShows,
    private val forceUpdateRepository: TvShowsForceUpdateRepository,
    private val getCachedTvShowsByType: GetCachedTvShowsByType,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    val container: Container<TopRatedTvShowsContract.State, TopRatedTvShowsContract.Effect> =
        Container(viewModelScope, TopRatedTvShowsContract.State.Init)

    init {
        viewModelScope.launch(coroutineDispatcherProvider.main()) {
            forceUpdateRepository.subscribeOnForceUpdate(this) { force -> if (force) refresh() }
        }
        savedLoadData()
    }

    private fun savedLoadData(forceUpdate: Boolean = false) {
        container.intent {
            try {
                loadData(forceUpdate = forceUpdate)
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private suspend fun loadData(forceUpdate: Boolean) {
        val cachedTvShows = getCachedTvShowsByType(
            CachedTvShowsParams(type = TvShowType.TOP_RATED)
        ).getOrDefault(emptyList())

        if (cachedTvShows.isNotEmpty()) {
            container.reduce {
                TopRatedTvShowsContract.State.Content(list = cachedTvShows.map { TvShowV2Item(it) })
            }
        } else {
            container.reduce {
                TopRatedTvShowsContract.State.Loading()
            }
            val result =
                getTopRatedTvShows(TopRatedTvShowsParams(forceUpdate = forceUpdate)).getOrThrow()

            container.reduce {
                TopRatedTvShowsContract.State.Content(list = result.map { TvShowV2Item(it) })
            }
        }
    }

    private fun refresh() = savedLoadData(forceUpdate = true)

    private suspend fun handleError(e: Exception) {
        val cachedMovies = getCachedTvShowsByType(
            CachedTvShowsParams(type = TvShowType.TOP_RATED)
        ).getOrDefault(emptyList())

        if (cachedMovies.isNotEmpty()) {
            container.reduce {
                TopRatedTvShowsContract.State.Content(list = cachedMovies.map { TvShowV2Item(it) })
            }
        } else {
            container.reduce {
                TopRatedTvShowsContract.State.Error(
                    list = listOf(
                        ErrorItem(
                            errorMessage = e.message.orEmpty(),
                            errorButtonVisible = false
                        )
                    ),
                    e = e
                )
            }
        }
    }

    fun openDetail(itemId: Int) {
        container.intent {
            container.postEffect(TopRatedTvShowsContract.Effect.OpenTvShowDetail(Navigate { navigator ->
                (navigator as TvShowsNavigator).forwardToTvShowDetailScreen(itemId)
            }))
        }
    }
}