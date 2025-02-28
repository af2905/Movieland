package com.github.af2905.movieland.tvshows.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.af2905.movieland.core.data.database.entity.TvShow
import com.github.af2905.movieland.core.data.database.entity.TvShowType
import com.github.af2905.movieland.core.repository.TvShowsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = TvShowsViewModel.Factory::class)
class TvShowsViewModel @AssistedInject constructor(
    @Assisted private val tvShowId: Int?,
    @Assisted private val tvShowType: TvShowType,
    tvShowsRepository: TvShowsRepository,
) : ViewModel() {

    var state by mutableStateOf(TvShowsState(tvShowType = tvShowType))
        private set

    private val _effect = Channel<TvShowsEffect>()
    val effect = _effect.receiveAsFlow()

    val tvShowsPager: Flow<PagingData<TvShow>> = when {
        tvShowType == TvShowType.SIMILAR && tvShowId != null -> {
            tvShowsRepository.getSimilarOrRecommendedPaginated(
                tvShowId = tvShowId,
                tvShowType = tvShowType,
                language = null
            )
        }

        tvShowType == TvShowType.RECOMMENDED && tvShowId != null -> {
            tvShowsRepository.getSimilarOrRecommendedPaginated(
                tvShowId = tvShowId,
                tvShowType = tvShowType,
                language = null
            )
        }

        else -> {
            tvShowsRepository.getTvShowsPaginated(tvShowType = tvShowType, language = null)
        }
    }.cachedIn(viewModelScope)


    fun onAction(action: TvShowsAction) {
        when (action) {
            is TvShowsAction.BackClick -> {
                viewModelScope.launch { _effect.send(TvShowsEffect.NavigateBack) }
            }

            is TvShowsAction.OpenTvShowDetail -> {
                viewModelScope.launch { _effect.send(TvShowsEffect.NavigateToTvShowDetail(action.tvShowId)) }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            tvShowId: Int?,
            tvShowType: TvShowType
        ): TvShowsViewModel
    }
}