package com.github.af2905.movieland.detail.tvshowdetail.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.common.network.ResultWrapper
import com.github.af2905.movieland.core.repository.TvShowsRepository
import com.github.af2905.movieland.detail.tvshowdetail.presentation.state.TvShowDetailsAction
import com.github.af2905.movieland.detail.tvshowdetail.presentation.state.TvShowDetailsEffect
import com.github.af2905.movieland.detail.tvshowdetail.presentation.state.TvShowDetailsState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = TvShowDetailsViewModel.Factory::class)
class TvShowDetailsViewModel @AssistedInject constructor(
    @Assisted private val tvShowId: Int,
    private val tvShowsRepository: TvShowsRepository
) : ViewModel() {

    var state by mutableStateOf(TvShowDetailsState())
        private set

    private val _effect = Channel<TvShowDetailsEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        fetchTvShowDetails()
    }

    private fun fetchTvShowDetails() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, isError = false)

            val tvShowDetailsResult = tvShowsRepository.getTvShowDetails(tvShowId, null)

            state = state.copy(
                tvShow = (tvShowDetailsResult as? ResultWrapper.Success)?.data,
                isError = tvShowDetailsResult is ResultWrapper.Error
            )

            // Collect Similar TV Shows as Flow
            launch {
                tvShowsRepository.getSimilarTvShows(tvShowId, null, null)
                    .collectLatest { result ->
                        state = state.copy(
                            similarTvShows = (result as? ResultWrapper.Success)?.data ?: emptyList()
                        )
                    }
            }

            // Collect Recommended TV Shows as Flow
            launch {
                tvShowsRepository.getRecommendedTvShows(tvShowId, null, null)
                    .collectLatest { result ->
                        state = state.copy(
                            recommendedTvShows = (result as? ResultWrapper.Success)?.data ?: emptyList()
                        )
                    }
            }

            // Collect Videos as Flow
            launch {
                tvShowsRepository.getTvShowVideos(tvShowId, null)
                    .collectLatest { result ->
                        state = state.copy(
                            videos = (result as? ResultWrapper.Success)?.data ?: emptyList()
                        )
                    }
            }

            // Collect Casts as Flow
            launch {
                tvShowsRepository.getTvShowCredits(tvShowId, null)
                    .collectLatest { result ->
                        state = state.copy(
                            casts = (result as? ResultWrapper.Success)?.data ?: emptyList()
                        )
                    }
            }

            state = state.copy(isLoading = false)
        }
    }

    fun onAction(action: TvShowDetailsAction) {
        when (action) {
            is TvShowDetailsAction.BackClick -> {
                viewModelScope.launch { _effect.send(TvShowDetailsEffect.NavigateBack) }
            }

            is TvShowDetailsAction.OpenPersonDetail -> {
                viewModelScope.launch { _effect.send(TvShowDetailsEffect.NavigateToPerson(action.personId)) }
            }

            is TvShowDetailsAction.OpenVideo -> {
                viewModelScope.launch { _effect.send(TvShowDetailsEffect.NavigateToVideo(action.videoId)) }
            }

            is TvShowDetailsAction.OpenTvShowsByType -> {
                viewModelScope.launch {
                    _effect.send(
                        TvShowDetailsEffect.NavigateToTvShows(
                            tvShowId = tvShowId,
                            tvShowType = action.tvShowType
                        )
                    )
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(tvShowId: Int): TvShowDetailsViewModel
    }
}