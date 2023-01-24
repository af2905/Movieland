package com.github.af2905.movieland.tvshows.presentation.popularTvShows

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
import com.github.af2905.movieland.core.shared.GetPopularTvShows
import com.github.af2905.movieland.core.shared.PopularTvShowsParams
import com.github.af2905.movieland.tvshows.presentation.TvShowsNavigator
import com.github.af2905.movieland.tvshows.repository.TvShowsForceUpdateRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class PopularTvShowsViewModel @Inject constructor(
    private val getPopularTvShows: GetPopularTvShows,
    private val forceUpdateRepository: TvShowsForceUpdateRepository,
    private val getCachedTvShowsByType: GetCachedTvShowsByType,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    val container: Container<PopularTvShowsContract.State, PopularTvShowsContract.Effect> =
        Container(viewModelScope, PopularTvShowsContract.State.Init)

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
            CachedTvShowsParams(type = TvShowType.POPULAR)
        ).getOrDefault(emptyList())

        if (cachedTvShows.isNotEmpty()) {
            container.reduce {
                PopularTvShowsContract.State.Content(list = cachedTvShows.map { TvShowV2Item(it) })
            }
        } else {
            container.reduce {
                PopularTvShowsContract.State.Loading()
            }
            val result =
                getPopularTvShows(PopularTvShowsParams(forceUpdate = forceUpdate)).getOrThrow()

            container.reduce {
                PopularTvShowsContract.State.Content(list = result.map { TvShowV2Item(it) })
            }
        }
    }

    private fun refresh() = savedLoadData(forceUpdate = true)

    private suspend fun handleError(e: Exception) {
        val cachedMovies = getCachedTvShowsByType(
            CachedTvShowsParams(type = TvShowType.POPULAR)
        ).getOrDefault(emptyList())

        if (cachedMovies.isNotEmpty()) {
            container.reduce {
                PopularTvShowsContract.State.Content(list = cachedMovies.map { TvShowV2Item(it) })
            }
        } else {
            container.reduce {
                PopularTvShowsContract.State.Error(
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
            container.postEffect(PopularTvShowsContract.Effect.OpenTvShowDetail(Navigate { navigator ->
                (navigator as TvShowsNavigator).forwardToTvShowDetailScreen(itemId)
            }))
        }
    }
}