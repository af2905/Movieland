package com.github.af2905.movieland.search.presentation.result

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.af2905.movieland.core.common.network.ResultWrapper
import com.github.af2905.movieland.core.data.database.entity.MovieType
import com.github.af2905.movieland.core.repository.SearchRepository
import com.github.af2905.movieland.core.repository.TrendingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val trendingRepository: TrendingRepository
) : ViewModel() {

    var state by mutableStateOf(SearchState())
        private set

    private val _effect = Channel<SearchEffect>()
    val effect = _effect.receiveAsFlow()

    private var searchJob: Job? = null

    init {
        fetchPopularSearches()
    }

    fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.UpdateQuery -> {
                state = state.copy(query = action.query)

                if (action.query.isNotBlank()) {
                    performSearch(action.query)
                } else {
                    fetchPopularSearches()
                }
            }

            is SearchAction.ClearQuery -> {
                state = state.copy(
                    query = "",
                    searchResult = emptyList(),
                    isError = false
                )
                fetchPopularSearches()
            }

            is SearchAction.OpenMovieDetail -> {
                viewModelScope.launch {
                    _effect.send(SearchEffect.NavigateToMovieDetail(action.movieId))
                }
            }

            is SearchAction.OpenTvShowDetail -> {
                viewModelScope.launch {
                    _effect.send(SearchEffect.NavigateToTvShowDetail(action.tvShowId))
                }
            }

            is SearchAction.OpenPersonDetail -> {
                viewModelScope.launch {
                    _effect.send(SearchEffect.NavigateToPersonDetail(action.personId))
                }
            }
        }
    }

    private fun performSearch(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            state = state.copy(isLoading = true, isError = false)

            try {
                val results = searchRepository.getSearchMulti(query = query, language = null)

                state = state.copy(
                    searchResult = results,
                    isLoading = false,
                    isError = results.isEmpty() // **Show "No results found" if empty**
                )
            } catch (e: Exception) {
                state = state.copy(
                    searchResult = emptyList(),
                    isLoading = false,
                    isError = true
                )
            }
        }
    }

    private fun fetchPopularSearches() {
        viewModelScope.launch {
            try {

                val response = trendingRepository.getTrendingMovies(
                    movieType = MovieType.TRENDING_WEEK,
                    null,
                    null
                )
                    .first { it is ResultWrapper.Success }

                when (response) {
                    is ResultWrapper.Success -> {
                        state = state.copy(
                            popularSearches = response.data.mapNotNull { it.title },
                            searchResult = emptyList(),
                            isError = false
                        )
                    }

                    else -> Unit
                }


            } catch (e: Exception) {
                state = state.copy(
                    popularSearches = emptyList()
                )
            }
        }
    }
}
