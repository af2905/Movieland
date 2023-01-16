package com.github.af2905.movieland.tvshows.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.common.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.tvshows.domain.TvShowsForceUpdate
import kotlinx.coroutines.launch
import javax.inject.Inject

class TvShowsViewModel @Inject constructor(
    private val forceUpdate: TvShowsForceUpdate,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    fun setForceUpdate() = viewModelScope.launch(coroutineDispatcherProvider.main()) {
        forceUpdate.invoke(Unit)
    }
}