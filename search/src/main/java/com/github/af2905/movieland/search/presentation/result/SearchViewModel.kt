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
class SearchViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(SearchState())
        private set

    private val _effect = Channel<SearchEffect>()
    val effect = _effect.receiveAsFlow()

    fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.UpdateQuery -> {
                state = state.copy(query = action.query)
            }
            is SearchAction.ClearQuery -> {
                state = state.copy(query = "")
            }
            is SearchAction.SubmitSearch -> {
                viewModelScope.launch {
                    _effect.send(SearchEffect.NavigateToResults(state.query))
                }
            }
        }
    }
}
