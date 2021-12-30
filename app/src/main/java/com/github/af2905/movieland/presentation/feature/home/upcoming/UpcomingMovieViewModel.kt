package com.github.af2905.movieland.presentation.feature.home.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.af2905.movieland.domain.usecase.movies.GetUpcomingMovies
import com.github.af2905.movieland.domain.usecase.params.UpcomingMoviesParams
import com.github.af2905.movieland.helper.CoroutineDispatcherProvider
import com.github.af2905.movieland.presentation.base.BaseViewModel
import com.github.af2905.movieland.presentation.feature.home.HomeNavigator
import com.github.af2905.movieland.presentation.feature.home.HomeRepository
import com.github.af2905.movieland.presentation.model.Model
import com.github.af2905.movieland.presentation.model.item.DividerItem
import com.github.af2905.movieland.presentation.model.item.MovieItemVariant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class UpcomingMovieViewModel @Inject constructor(
    private val getUpcomingMovies: GetUpcomingMovies,
    private val homeRepository: HomeRepository,
    coroutineDispatcherProvider: CoroutineDispatcherProvider
) : BaseViewModel<HomeNavigator>(coroutineDispatcherProvider) {

    private val _items = MutableLiveData<List<Model>>()
    val items: LiveData<List<Model>> = _items

    init {
        loadData()
        launchIO {
            homeRepository.subscribeOnForceUpdate(this) { force -> if (force) refresh() }
        }
    }

    private fun loadData() {
        launchUI {
            loading.emit(true)
            val upcoming = loadUpcomingMoviesAsync(this)
            _items.value = upcoming.await().getOrDefault(emptyList())
            loading.emit(false)
        }
    }

    private suspend fun loadUpcomingMoviesAsync(coroutineScope: CoroutineScope): Deferred<Result<List<Model>>> {
        val deferredUpcoming = coroutineScope.iOAsync {
            val upcomingMovies =
                getUpcomingMovies(UpcomingMoviesParams()).getOrThrow().movies
                    ?.map { MovieItemVariant(it) } ?: emptyList()
            if (!upcomingMovies.isNullOrEmpty()) {
                mutableListOf<Model>().apply {
                    upcomingMovies.map { addAll(listOf(it, DividerItem())) }
                }
            } else emptyList()
        }
        return deferredUpcoming
    }

    private fun refresh() = loadData()

    fun openDetail(itemId: Int, position: Int) = navigator { forwardMovieDetail(itemId) }
}