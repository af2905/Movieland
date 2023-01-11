package com.github.af2905.movieland.movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.common.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.movies.domain.MoviesForceUpdate
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    private val forceUpdate: MoviesForceUpdate,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    fun setForceUpdate() = viewModelScope.launch(coroutineDispatcherProvider.main()) {
        forceUpdate.invoke(Unit)
    }
}