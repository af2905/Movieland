package com.github.af2905.movieland.search.presentation.result

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(SearchResultState())
        private set

    private val _effect = Channel<SearchResultEffect>()
    val effect = _effect.receiveAsFlow()

    fun onAction(action: SearchResultAction) {
        when (action) {
            is SearchResultAction.UpdateQuery -> {
                state = state.copy(query = action.query)
            }
            is SearchResultAction.ClearQuery -> {
                state = state.copy(query = "")
            }
            is SearchResultAction.SubmitSearch -> {
                viewModelScope.launch {
                    _effect.send(SearchResultEffect.NavigateToResults(state.query))
                }
            }
        }
    }
}
