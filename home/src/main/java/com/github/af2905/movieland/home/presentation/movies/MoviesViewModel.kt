package com.github.af2905.movieland.home.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.common.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.home.domain.usecase.ForceUpdate
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    private val forceUpdate: ForceUpdate,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    fun setForceUpdate() = viewModelScope.launch(coroutineDispatcherProvider.main()) {
        forceUpdate.invoke(Unit)
    }
}